package top.catcatc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.catcatc.service.LabelsForArticlesService;
import top.catcatc.mapper.mapper.LabelsForArticlesMapper;
import top.catcatc.common.pojo.entity.LabelsForArticles;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Service
public class LabelsForArticlesServiceImpl extends ServiceImpl<LabelsForArticlesMapper, LabelsForArticles> implements LabelsForArticlesService {
}
