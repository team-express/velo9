package teamexpress.velo9.post.domain;

import static teamexpress.velo9.post.domain.QPostTag.postTag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PostTagQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public PostTagQueryRepository(EntityManager em) {
		this.jpaQueryFactory = new JPAQueryFactory(em);
	}

	public List<PostTag> findByPost(Post findPost) {
		return jpaQueryFactory
			.selectFrom(postTag)
			.join(postTag.tag).fetchJoin()
			.where(postTag.post.eq(findPost))
			.fetch();
	}

}
