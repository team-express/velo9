package teamexpress.velo9.post.domain;

import static teamexpress.velo9.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import teamexpress.velo9.post.dto.PostReadDTO;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PostRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Slice<PostReadDTO> findPost(String nickname, Pageable pageable) {
		List<Post> postList = queryFactory
			.selectFrom(post)
			.where(post.member.nickname.eq(nickname))
			.fetch();

		List<PostReadDTO> result =
			postList.stream().map(PostReadDTO::new).limit(10)
				.collect(Collectors.toList());

		JPAQuery<Post> countQuery = queryFactory
			.selectFrom(post);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchCount);
	}
}
