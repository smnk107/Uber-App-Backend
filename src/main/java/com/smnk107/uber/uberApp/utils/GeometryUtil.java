package com.smnk107.uber.uberApp.utils;

import com.smnk107.uber.uberApp.dto.PointDTO;
import org.locationtech.jts.awt.PointShapeFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.Point;


public class GeometryUtil {

    public static Point createPoint(PointDTO pointDTO)
    {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(),4326);
        Coordinate coordinate = new Coordinate(pointDTO.getCoordinates()[0],pointDTO.getCoordinates()[1]);
        Point point = geometryFactory.createPoint(coordinate);

        System.out.println(point.getX()+"--in util--"+point.getY());

        return point;
    }
}
