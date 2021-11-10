package top.catcatc.common.pojo;

import top.catcatc.common.pojo.blog.bo.*;
import top.catcatc.common.pojo.blog.dto.BlogDTO;
import top.catcatc.common.pojo.blog.dto.LabelDTO;
import top.catcatc.common.pojo.blog.dto.ListLabelsByNumberDTO;
import top.catcatc.common.pojo.blog.vo.BlogVO;
import top.catcatc.common.pojo.blog.vo.LabelVO;
import top.catcatc.common.pojo.entity.Label;
import top.catcatc.common.pojo.request.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王金义
 * @date 2021/8/30
 */
@Component
public class Converter {

    private static final String WHITE = "#FFFFFF";
    private static final String BLACK = "#000000";
    /**
     * RPG 之和的一半
     */
    private static final Integer HALF_OF_SUM_OF_RPG = (255 + 255 + 255) /2;

    public static BlogBO blogBO(IssueWebHookRequest issueWebHookRequest) {
        return BlogBO.builder()
                .number(issueWebHookRequest.getIssue().getNumber())
                .title(issueWebHookRequest.getIssue().getTitle())
                .state(issueWebHookRequest.getIssue().getState())
                .createdAt(issueWebHookRequest.getIssue().getCreatedAt())
                .updatedAt(issueWebHookRequest.getIssue().getUpdatedAt())
                .build();
    }

    public static LabelDTO labelDTO(LabelBO labelBO) {
        return LabelDTO.builder()
                .id(labelBO.getId())
                .name(labelBO.getName())
                .color(labelBO.getColor())
                .description(labelBO.getDescription())
                .build();
    }

    public static LabelDTO labelDTO(ListLabelsByNumberDTO listLabelsByNumberDTO) {
        return LabelDTO.builder()
                .id(listLabelsByNumberDTO.getId())
                .name(listLabelsByNumberDTO.getName())
                .color(listLabelsByNumberDTO.getColor())
                .description(listLabelsByNumberDTO.getDescription())
                .build();
    }

    public static LabelBO labelBO(LabelWebHookRequest labelWebHookRequest) {
        LabelWebHookRequestBody label = labelWebHookRequest.getLabel();
        return LabelBO.builder()
                .id(label.getId())
                .nodeId(label.getNodeId())
                .url(label.getUrl())
                .name(label.getName())
                .color(label.getColor())
                .description(label.getDescription())
                .build();
    }

    public static ArticleBO articleBO(String body, String bNumber, String toc) {
        return ArticleBO.builder()
                .body(body)
                .bNumber(bNumber)
                .toc(toc)
                .build();
    }


    public static LabelsForBlogsBO labelsForBlogsBO(IssueWebHookRequest issueWebHookRequest) {
        return LabelsForBlogsBO.builder()
                .bNumber(issueWebHookRequest.getIssue().getNumber())
                .lId(issueWebHookRequest.getLabel().getId())
                .build();
    }

    public static MarkdownBody markdownBody(String body) {
        return MarkdownBody.builder()
                .text(body)
                .build();
    }

    public static LabelVO labelVO(LabelDTO labelDTO) {
        String color = labelDTO.getColor();
        return LabelVO.builder()
                .id(labelDTO.getId())
                .name(labelDTO.getName())
                .color(color)
                .description(labelDTO.getDescription())
                .fontColor(getFontColor(color))
                .build();
    }

    public static LabelVO labelVO(LabelBO labelBO) {
        String color = labelBO.getColor();
        return LabelVO.builder()
                .id(labelBO.getId())
                .name(labelBO.getName())
                .color(color)
                .description(labelBO.getDescription())
                .fontColor(getFontColor(color))
                .build();
    }

    public static LabelVO labelVO(Label label) {
        final String color = label.getColor();
        return LabelVO.builder()
                .id(label.getId())
                .name(label.getName())
                .color(color)
                .fontColor(getFontColor(color))
                .description(label.getDescription())
                .build();
    }

    public static LabelVO setFontColor(LabelVO labelVO) {
        labelVO.setFontColor(getFontColor(labelVO.getColor()));
        return labelVO;
    }

    public static List<LabelVO> setFontColor(List<LabelVO> labelVOS) {
        for (LabelVO labelVO : labelVOS) {
            labelVO.setFontColor(getFontColor(labelVO.getColor()));
        }
        return labelVOS;
    }

    public static BlogVO blogVO(BlogDTO blogDTO) {
        List<LabelDTO> labels = blogDTO.getLabels();
        List<LabelVO> labelVOS = new ArrayList<>();
        for (LabelDTO label : labels) {
            labelVOS.add(labelVO(label));
        }
        return BlogVO.builder()
                .number(blogDTO.getNumber())
                .title(blogDTO.getTitle())
                .state(blogDTO.getState())
                .body(blogDTO.getBody())
                .toc(blogDTO.getToc())
                .labels(labelVOS)
                .createdAt(blogDTO.getCreatedAt())
                .updatedAt(blogDTO.getUpdatedAt())
                .cover(blogDTO.getCover())
                .build();
    }

    public static ListBlogsByNumberPageBO listBlogsByNumberPageBO(List<String> numbers, Integer page, Integer perPage) {
        return ListBlogsByNumberPageBO.builder()
                .numbers(numbers)
                .page(page)
                .perPage(perPage)
                .build();
    }

    public static ListBlogsNumberPage listBlogsNumberPage(Integer page, Integer perPage) {
        return ListBlogsNumberPage.builder()
                .page(page)
                .perPage(perPage)
                .build();
    }

    /**
     * 颜色的 RGB 值取平均值, 如果平均值 < (255 * 3 / 2) 则颜色偏暗, 取白色, 反之
     * @param color 标签颜色
     * @return 文字颜色
     */
    private static String getFontColor(String color) {
        String replace = color.replace("#", "");
        String hex01 = replace.substring(0, 2);
        String hex23 = replace.substring(2, 4);
        String hex45 = replace.substring(4, 6);
        int decimal01 = Integer.parseInt(hex01, 16);
        int decimal23 = Integer.parseInt(hex23, 16);
        int decimal45 = Integer.parseInt(hex45, 16);
        if (decimal01 + decimal23 + decimal45 < HALF_OF_SUM_OF_RPG) {
            return WHITE;
        } else {
            return BLACK;
        }
    }
}
