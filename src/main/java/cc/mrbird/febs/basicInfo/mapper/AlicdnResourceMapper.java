package cc.mrbird.febs.basicInfo.mapper;

import cc.mrbird.febs.basicInfo.entity.AlicdnResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author wsq
 * @date 2019-12-20 14:36:46
 */
public interface AlicdnResourceMapper extends BaseMapper<AlicdnResource> {

    List<AlicdnResource> findAlicdnResources(AlicdnResource alicdnResource);
}
