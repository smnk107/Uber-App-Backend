package com.smnk107.uber.uberApp.services;

//import java.awt.*;

import org.locationtech.jts.geom.Point;

public interface DistanceService {

    Double calculateDistance(Point source, Point destination);
}
