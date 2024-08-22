package com.smnk107.uber.uberApp.configs;

import com.smnk107.uber.uberApp.dto.PointDTO;
import com.smnk107.uber.uberApp.utils.GeometryUtil;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper mapperBean()
    {
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(PointDTO.class, Point.class).setConverter(context->{
            PointDTO pointDTO = context.getSource();
            System.out.println(pointDTO.getCoordinates()[0]+"--in--"+pointDTO.getCoordinates()[1]);
            return GeometryUtil.createPoint(pointDTO);
        });

        mapper.typeMap(Point.class,PointDTO.class).setConverter(context->{
            Double coordinates[] = {context.getSource().getX(),context.getSource().getY()};
            return new PointDTO(coordinates);
        });

        return mapper;
    }
}
