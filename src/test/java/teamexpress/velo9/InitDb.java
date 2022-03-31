package teamexpress.velo9;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.domain.Look;
import teamexpress.velo9.member.domain.LookRepository;
import teamexpress.velo9.member.domain.Love;
import teamexpress.velo9.member.domain.LoveRepository;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.MemberRepository;
import teamexpress.velo9.member.domain.MemberThumbnail;
import teamexpress.velo9.member.domain.MemberThumbnailRepository;
import teamexpress.velo9.member.domain.Role;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostAccess;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.domain.Series;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.domain.Tag;
import teamexpress.velo9.post.domain.TagRepository;

@Profile("test")
@Component
@RequiredArgsConstructor
public class InitDb {

	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.dbInit1();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {

		private final PostRepository postRepository;
		private final MemberRepository memberRepository;
		private final PasswordEncoder passwordEncoder;
		private final SeriesRepository seriesRepository;
		private final PostThumbnailRepository postThumbnailRepository;
		private final TagRepository tagRepository;
		private final LoveRepository loveRepository;
		private final LookRepository lookRepository;
		private final MemberThumbnailRepository memberThumbnailRepository;

		public void dbInit1() {

			MemberThumbnail thumbnail1 = MemberThumbnail.builder().uuid("uuid").name("name").path("path").build();
			MemberThumbnail thumbnail2 = MemberThumbnail.builder().uuid("uuid").name("name").path("path").build();
			MemberThumbnail thumbnail3 = MemberThumbnail.builder().uuid("uuid").name("name").path("path").build();
			memberThumbnailRepository.save(thumbnail1);
			memberThumbnailRepository.save(thumbnail2);
			memberThumbnailRepository.save(thumbnail3);

			memberRepository.save(createMember("jinwook", "jinwook", "1234", "test1@nate.com", Role.ROLE_USER, thumbnail1));
			memberRepository.save(createMember("admin", "admin", "1234", "test2@nate.com", Role.ROLE_SOCIAL, thumbnail2));
			memberRepository.save(createMember("test", "test", "1234", "test3@nate.com", Role.ROLE_SOCIAL, thumbnail3));
			memberRepository.save(createSocialMember("test4@nate.com", Role.ROLE_SOCIAL));

			Member member = memberRepository.findByNickname("admin").get();

			Series series1 = createSeries("series1", member);
			Series series2 = createSeries("series2", member);
			Series series3 = createSeries("series3", member);
			Series series4 = createSeries("series4", member);
			Series series5 = createSeries("series5", member);
			Series series6 = createSeries("series6", member);
			Series series7 = createSeries("series7", member);
			Series series8 = createSeries("series8", member);
			Series series9 = createSeries("series9", member);
			Series series10 = createSeries("series10", member);
			seriesRepository.save(series1);
			seriesRepository.save(series2);
			seriesRepository.save(series3);
			seriesRepository.save(series4);
			seriesRepository.save(series5);
			seriesRepository.save(series6);
			seriesRepository.save(series7);
			seriesRepository.save(series8);
			seriesRepository.save(series9);
			seriesRepository.save(series10);

			Tag tag1 = new Tag("tag1");
			Tag tag2 = new Tag("tag2");
			Tag tag3 = new Tag("tag3");
			Tag tag4 = new Tag("tag4");
			Tag tag5 = new Tag("tag5");
			tagRepository.save(tag1);
			tagRepository.save(tag2);
			tagRepository.save(tag3);
			tagRepository.save(tag4);
			tagRepository.save(tag5);

			PostThumbnail postThumbnail = createThumbnail("path", "uuid", "name");
			postThumbnailRepository.save(postThumbnail);

			for (int i = 1; i < 4; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series1, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
			}

			for (int i = 5; i < 7; i++) {
				Post savedPost = postRepository.save(createTemp("" + i, "" + i, member, series1, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
			}
		}

		private Post createPost(String title, String introduce, Member member, Series series, PostThumbnail postThumbnail) {
			return Post.builder().access(PostAccess.PUBLIC).title(title).introduce(introduce).member(member).series(series).postThumbnail(postThumbnail).status(PostStatus.GENERAL).build();
		}

		private Post createTemp(String title, String introduce, Member member, Series series, PostThumbnail postThumbnail) {
			return Post.builder().access(PostAccess.PUBLIC).title(title).introduce(introduce).member(member).series(series).postThumbnail(postThumbnail).status(PostStatus.TEMPORARY).build();
		}

		private PostThumbnail createThumbnail(String path, String uuid, String name) {
			return PostThumbnail.builder().path(path).uuid(uuid).name(name).build();
		}

		private Series createSeries(String name, Member member) {
			return Series.builder().name(name).member(member).posts(new ArrayList<>()).build();
		}

		private Member createMember(String nickname, String username, String password, String email, Role roleUser, MemberThumbnail memberThumbnail) {
			return Member.builder().memberThumbnail(memberThumbnail).nickname(nickname).blogTitle(nickname).username(username).password(passwordEncoder.encode(password)).email(email).posts(new ArrayList<>()).role(roleUser).build();
		}

		private Member createSocialMember(String email, Role roleUser) {
			return Member.builder().email(email).posts(new ArrayList<>()).role(roleUser).build();
		}

		private Love createLove(Member member, Post post) {
			return Love.builder().member(member).post(post).build();
		}

		private Look createLook(Member member, Post post) {
			return Look.builder().member(member).post(post).build();
		}
	}
}
