package teamexpress.velo9;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.domain.Look;
import teamexpress.velo9.member.domain.LookRepository;
import teamexpress.velo9.member.domain.Love;
import teamexpress.velo9.member.domain.LoveRepository;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.MemberRepository;
import teamexpress.velo9.member.domain.Role;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.PostTag;
import teamexpress.velo9.post.domain.PostTagRepository;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.domain.Series;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.domain.Tag;
import teamexpress.velo9.post.domain.TagRepository;

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
		private final PostTagRepository postTagRepository;

		public void dbInit1() {
			memberRepository.save(createMember("jinwook", "jinwook", "1234", "test@nate.com", Role.ROLE_USER));
			memberRepository.save(createMember("admin", "admin", "1234", "test@nate.com", Role.ROLE_SOCIAL));
			memberRepository.save(createMember("test", "test", "1234", "test@nate.com", Role.ROLE_SOCIAL));

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

			for (int i = 0; i < 10; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series1, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
				postTagRepository.save(createTagPost(savedPost, tag1));
				postTagRepository.save(createTagPost(savedPost, tag2));
				postTagRepository.save(createTagPost(savedPost, tag3));

			}
			for (int i = 10; i < 20; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series1, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
				postTagRepository.save(createTagPost(savedPost, tag2));
			}
			for (int i = 20; i < 30; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series1, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
				postTagRepository.save(createTagPost(savedPost, tag3));
			}
			for (int i = 30; i < 40; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series1, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));

			}
			for (int i = 40; i < 50; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series1, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));

			}
			for (int i = 50; i < 60; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series6, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
				postTagRepository.save(createTagPost(savedPost, tag2));
				postTagRepository.save(createTagPost(savedPost, tag3));
			}
			for (int i = 60; i < 70; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series7, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
			}
			for (int i = 70; i < 80; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series8, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
			}
			for (int i = 80; i < 90; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series9, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
			}
			for (int i = 90; i < 100; i++) {
				Post savedPost = postRepository.save(createPost("" + i, "" + i, member, series10, postThumbnail));
				loveRepository.save(createLove(member, savedPost));
				lookRepository.save(createLook(member, savedPost));
			}
		}

		private Post createPost(String title, String introduce, Member member, Series series, PostThumbnail postThumbnail) {
			return Post.builder().title(title).introduce(introduce).member(member).series(series).postThumbnail(postThumbnail).status(PostStatus.GENERAL).build();
		}

		private PostThumbnail createThumbnail(String path, String uuid, String name) {
			return PostThumbnail.builder().path(path).uuid(uuid).name(name).build();
		}

		private Series createSeries(String name, Member member) {
			return Series.builder().name(name).member(member).posts(new ArrayList<>()).build();
		}

		private Member createMember(String nickname, String username, String password, String email, Role roleUser) {
			return Member.builder().nickname(nickname).blogTitle(nickname).username(username).password(passwordEncoder.encode(password)).email(email).posts(new ArrayList<>()).role(roleUser).build();
		}

		private Love createLove(Member member, Post post) {
			return Love.builder().member(member).post(post).build();
		}

		private Look createLook(Member member, Post post) {
			return Look.builder().member(member).post(post).build();
		}

		private PostTag createTagPost(Post post, Tag tag) {
			return PostTag.builder().post(post).tag(tag).build();
		}
	}
}
