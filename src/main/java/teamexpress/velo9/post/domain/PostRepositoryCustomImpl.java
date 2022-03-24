package teamexpress.velo9.post.domain;

import static teamexpress.velo9.post.domain.QPost.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.function.Supplier;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import teamexpress.velo9.post.dto.SearchCondition;

public class PostRepositoryCustomImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PostRepositoryCustomImpl(EntityManager em) {
		super(Post.class);
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Slice<Post> findReadPost(String nickname, Pageable pageable) {
		List<Post> content = queryFactory.selectFrom(post)
			.join(post.postThumbnail).fetchJoin()
			.where(post.member.nickname.eq(nickname))
			.where(openPost())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = isHasNext(content, pageable);

		return new SliceImpl<>(content, pageable, hasNext);
	}

	@Override
	public Page<Post> search(SearchCondition condition, Pageable pageable) {

		JPAQuery<Post> query = queryFactory
			.selectFrom(post)
			.join(post.member).fetchJoin()
			.join(post.postThumbnail).fetchJoin()
			.where(searchContent(condition.getContent()))
			.where(openPost())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<Post> content = getQuerydsl().applyPagination(pageable, query).fetch();

		JPAQuery<Post> countQuery = queryFactory.selectFrom(post);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
	}

	private boolean isHasNext(List<Post> result, Pageable pageable) {
		boolean hasNext = false;
		if (result.size() > pageable.getPageSize()) {
			result.remove(pageable.getPageSize());
			hasNext = true;
		}
		return hasNext;
	}

	private BooleanBuilder searchContent(String content) {
		return titleContains(content).or(contentContains(content));
	}

	private BooleanBuilder titleContains(String content) {
		return nullSafeBuilder(() -> post.title.contains(content));
	}

	private BooleanBuilder contentContains(String content) {
		return nullSafeBuilder(() -> post.content.contains(content));
	}

	private BooleanBuilder openPost() {
		return status().and(access());
	}

	private BooleanBuilder status() {
		return nullSafeBuilder(() -> post.status.eq(PostStatus.GENERAL));
	}

	private BooleanBuilder access() {
		return nullSafeBuilder(() -> post.access.eq(PostAccess.PUBLIC));
	}

	private static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
		try {
			return new BooleanBuilder(f.get());
		} catch (NullPointerException e) {
			return new BooleanBuilder();
		}
	}
}
