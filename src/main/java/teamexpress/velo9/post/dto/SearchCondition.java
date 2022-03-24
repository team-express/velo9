package teamexpress.velo9.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCondition {
	boolean tagSelect;
	String content;
}
