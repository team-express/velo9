package teamexpress.velo9.post.domain;

import static teamexpress.velo9.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PostRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Slice<Post> findReadPost(String nickname, Pageable pageable) {
		List<Post> content = queryFactory
			.selectFrom(post)
			.join(post.postThumbnail).fetchJoin()
			.where(post.member.nickname.eq(nickname))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = isHasNext(content, pageable);

		return new SliceImpl<>(content, pageable, hasNext);
	}

	private boolean isHasNext(List<Post> result, Pageable pageable) {
		boolean hasNext = false;
		if (result.size() > pageable.getPageSize()) {
			result.remove(pageable.getPageSize());
			hasNext = true;
		}
		return hasNext;
	}
}
