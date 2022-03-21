package teamexpress.velo9.post.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SeriesRepositoryTest {

	@Autowired
	private SeriesRepository seriesRepository;

	@Test
	void findSeries() {
		//given
		String nickname = "admin";
		PageRequest pageRequest = PageRequest.of(0, 5);

		//when
		Slice<Series> findSeries = seriesRepository.findPostBySeriesName(nickname, pageRequest);

		//then
		assertThat(findSeries.getNumberOfElements()).isEqualTo(5);
		assertThat(findSeries).extracting("name").contains("series1", "series3", "series4", "series5", "series6");
	}
}
