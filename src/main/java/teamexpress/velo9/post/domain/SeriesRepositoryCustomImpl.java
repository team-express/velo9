package teamexpress.velo9.post.domain;

import static teamexpress.velo9.post.domain.QPost.post;
import static teamexpress.velo9.post.domain.QSeries.series;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import teamexpress.velo9.post.dto.SeriesDTO;

public class SeriesRepositoryCustomImpl implements SeriesRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public SeriesRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Slice<SeriesDTO> findPostBySeriesName(String nickname, Pageable pageable) {

		List<Series> seriesList = queryFactory
			.selectDistinct(series)
			.from(series)
			.leftJoin(series.posts, post)
			.where(post.member.nickname.eq(nickname))
			.fetch();

		List<SeriesDTO> result = seriesList.stream().map(SeriesDTO::new).limit(pageable.getPageSize())
			.collect(Collectors.toList());

		JPAQuery<Series> countQuery = queryFactory
			.selectFrom(series);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchCount);
	}
}