package top.catcatc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.catcatc.service.LabelService;
import top.catcatc.mapper.mapper.LabelMapper;
import top.catcatc.common.pojo.entity.Label;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {
}
