package net.pl3x.map.core.markers.marker;

import java.util.List;
import net.pl3x.map.core.Keyed;
import net.pl3x.map.core.markers.JsonSerializable;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.util.Preconditions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;


/**
 * Represents a map marker.
 */
public abstract class Marker<T extends Marker<T>> extends Keyed implements JsonSerializable {
    private final String type;
    private String pane;
    private Options options;

    /**
     * Create a new marker.
     *
     * @param type type of marker
     * @param key  identifying key
     */
    public Marker(@NonNull String type, @NonNull String key) {
        super(key);
        this.type = Preconditions.checkNotNull(type, "Marker type is null");
    }

    /**
     * Create a new circle.
     *
     * @param key     identifying key
     * @param centerX center x location
     * @param centerZ center z location
     * @param radius  circle radius
     * @return a new circle
     */
    @NonNull
    public static Circle circle(@NonNull String key, double centerX, double centerZ, double radius) {
        return Circle.of(key, centerX, centerZ, radius);
    }

    /**
     * Create a new circle.
     *
     * @param key    identifying key
     * @param center center location
     * @param radius circle radius
     * @return a new circle
     */
    @NonNull
    public static Circle circle(@NonNull String key, @NonNull Point center, double radius) {
        return Circle.of(key, center, radius);
    }

    /**
     * Create a new ellipse.
     *
     * @param key     identifying key
     * @param centerX center x location
     * @param centerZ center z location
     * @param radiusX x radius
     * @param radiusZ z radius
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, double centerX, double centerZ, double radiusX, double radiusZ) {
        return Ellipse.of(key, centerX, centerZ, radiusX, radiusZ);
    }

    /**
     * Create a new ellipse.
     *
     * @param key     identifying key
     * @param center  center location
     * @param radiusX x radius
     * @param radiusZ z radius
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, @NonNull Point center, double radiusX, double radiusZ) {
        return Ellipse.of(key, center, radiusX, radiusZ);
    }

    /**
     * Create a new ellipse.
     *
     * @param key     identifying key
     * @param centerX center x location
     * @param centerZ center z location
     * @param radius  radius
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, double centerX, double centerZ, @NonNull Vector radius) {
        return Ellipse.of(key, centerX, centerZ, radius);
    }

    /**
     * Create a new ellipse.
     *
     * @param key    identifying key
     * @param center center location
     * @param radius radius
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, @NonNull Point center, @NonNull Vector radius) {
        return Ellipse.of(key, center, radius);
    }

    /**
     * Create a new ellipse.
     *
     * @param key     identifying key
     * @param centerX center x location
     * @param centerZ center z location
     * @param radiusX x radius
     * @param radiusZ z radius
     * @param tilt    tilt
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, double centerX, double centerZ, double radiusX, double radiusZ, double tilt) {
        return Ellipse.of(key, centerX, centerZ, radiusX, radiusZ, tilt);
    }

    /**
     * Create a new ellipse.
     *
     * @param key     identifying key
     * @param center  center location
     * @param radiusX x radius
     * @param radiusZ z radius
     * @param tilt    tilt
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, @NonNull Point center, double radiusX, double radiusZ, double tilt) {
        return Ellipse.of(key, center, radiusX, radiusZ, tilt);
    }

    /**
     * Create a new ellipse.
     *
     * @param key     identifying key
     * @param centerX center x location
     * @param centerZ center z location
     * @param radius  radius
     * @param tilt    tilt
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, double centerX, double centerZ, @NonNull Vector radius, double tilt) {
        return Ellipse.of(key, centerX, centerZ, radius, tilt);
    }

    /**
     * Create a new ellipse.
     *
     * @param key    identifying key
     * @param center center location
     * @param radius radius
     * @param tilt   tilt
     * @return a new ellipse
     */
    @NonNull
    public static Ellipse ellipse(@NonNull String key, @NonNull Point center, @NonNull Vector radius, double tilt) {
        return Ellipse.of(key, center, radius, tilt);
    }

    /**
     * Create a new icon.
     *
     * @param key   identifying key
     * @param x     icon x location on map
     * @param z     icon z location on map
     * @param image image key
     * @return a new icon
     */
    @NonNull
    public static Icon icon(@NonNull String key, double x, double z, @NonNull String image) {
        return Icon.of(key, x, z, image);
    }

    /**
     * Create a new icon.
     *
     * @param key   identifying key
     * @param point icon location on map
     * @param image image key
     * @return a new icon
     */
    @NonNull
    public static Icon icon(@NonNull String key, @NonNull Point point, @NonNull String image) {
        return Icon.of(key, point, image);
    }

    /**
     * Create a new icon.
     *
     * @param key   identifying key
     * @param x     icon x location on map
     * @param z     icon z location on map
     * @param image image key
     * @param size  size of image
     * @return a new icon
     */
    @NonNull
    public static Icon icon(@NonNull String key, double x, double z, @NonNull String image, double size) {
        return Icon.of(key, x, z, image, size, size);
    }

    /**
     * Create a new icon.
     *
     * @param key    identifying key
     * @param x      icon x location on map
     * @param z      icon z location on map
     * @param image  image key
     * @param width  width of image
     * @param height height of image
     * @return a new icon
     */
    @NonNull
    public static Icon icon(@NonNull String key, double x, double z, @NonNull String image, double width, double height) {
        return Icon.of(key, x, z, image, width, height);
    }

    /**
     * Create a new icon.
     *
     * @param key   identifying key
     * @param point icon location on map
     * @param image image key
     * @param size  size of image
     * @return a new icon
     */
    @NonNull
    public static Icon icon(@NonNull String key, @NonNull Point point, @NonNull String image, double size) {
        return Icon.of(key, point, image, size, size);
    }

    /**
     * Create a new icon.
     *
     * @param key    identifying key
     * @param point  icon location on map
     * @param image  image key
     * @param width  width of image
     * @param height height of image
     * @return a new icon
     */
    @NonNull
    public static Icon icon(@NonNull String key, @NonNull Point point, @NonNull String image, double width, double height) {
        return Icon.of(key, point, image, width, height);
    }

    /**
     * Create a new icon.
     *
     * @param key   identifying key
     * @param point icon location on map
     * @param image image key
     * @param size  size of image
     * @return a new icon
     */
    @NonNull
    public static Icon icon(@NonNull String key, @NonNull Point point, @NonNull String image, @Nullable Vector size) {
        return Icon.of(key, point, image, size);
    }

    /**
     * Create a new multi-polygon.
     *
     * @param key     identifying key
     * @param polygon polygon to add
     * @return a new multi-polygon
     */
    @NonNull
    public static MultiPolygon multiPolygon(@NonNull String key, @NonNull Polygon polygon) {
        return MultiPolygon.of(key, polygon);
    }

    /**
     * Create a new multi-polygon.
     *
     * @param key      identifying key
     * @param polygons polygons to add
     * @return a new multi-polygon
     */
    @NonNull
    public static MultiPolygon multiPolygon(@NonNull String key, @NonNull Polygon @NonNull ... polygons) {
        return MultiPolygon.of(key, polygons);
    }

    /**
     * Create a new multi-polygon.
     *
     * @param key      identifying key
     * @param polygons polygons to add
     * @return a new multi-polygon
     */
    @NonNull
    public static MultiPolygon multiPolygon(@NonNull String key, @NonNull List<Polygon> polygons) {
        return MultiPolygon.of(key, polygons);
    }

    /**
     * Create a new multi-polyline.
     *
     * @param key      identifying key
     * @param polyline polyline to add
     * @return a new multi-polyline
     */
    @NonNull
    public static MultiPolyline multiPolyline(@NonNull String key, @NonNull Polyline polyline) {
        return MultiPolyline.of(key, polyline);
    }

    /**
     * Create a new multi-polyline.
     *
     * @param key       identifying key
     * @param polylines polylines to add
     * @return a new multi-polyline
     */
    @NonNull
    public static MultiPolyline multiPolyline(@NonNull String key, @NonNull Polyline @NonNull ... polylines) {
        return MultiPolyline.of(key, polylines);
    }

    /**
     * Create a new multi-polyline.
     *
     * @param key       identifying key
     * @param polylines polylines to add
     * @return a new multi-polyline
     */
    @NonNull
    public static MultiPolyline multiPolyline(@NonNull String key, @NonNull List<Polyline> polylines) {
        return MultiPolyline.of(key, polylines);
    }

    /**
     * Create a new polygon.
     *
     * @param key      identifying key
     * @param polyline polyline to add
     * @return a new polygon
     */
    @NonNull
    public static Polygon polygon(@NonNull String key, @NonNull Polyline polyline) {
        return Polygon.of(key, polyline);
    }

    /**
     * Create a new polygon.
     *
     * @param key       identifying key
     * @param polylines polylines to add
     * @return a new polygon
     */
    @NonNull
    public static Polygon polygon(@NonNull String key, @NonNull Polyline @NonNull ... polylines) {
        return Polygon.of(key, polylines);
    }

    /**
     * Create a new polygon.
     *
     * @param key       identifying key
     * @param polylines polylines to add
     * @return a new polygon
     */
    @NonNull
    public static Polygon polygon(@NonNull String key, @NonNull List<Polyline> polylines) {
        return Polygon.of(key, polylines);
    }

    /**
     * Create a new polyline.
     *
     * @param key   identifying key
     * @param point point to add
     * @return a new polyline
     */
    @NonNull
    public static Polyline polyline(@NonNull String key, @NonNull Point point) {
        return Polyline.of(key, point);
    }

    /**
     * Create a new polyline.
     *
     * @param key    identifying key
     * @param points points to add
     * @return a new polyline
     */
    @NonNull
    public static Polyline polyline(@NonNull String key, @NonNull Point @NonNull ... points) {
        return Polyline.of(key, points);
    }

    /**
     * Create a new polyline.
     *
     * @param key    identifying key
     * @param points points to add
     * @return a new polyline
     */
    @NonNull
    public static Polyline polyline(@NonNull String key, @NonNull List<Point> points) {
        return Polyline.of(key, points);
    }

    /**
     * Create a new rectangle.
     *
     * @param key identifying key
     * @param x1  first x point
     * @param z1  first z point
     * @param x2  second x point
     * @param z2  second z point
     * @return a new rectangle
     */
    @NonNull
    public static Rectangle rectangle(@NonNull String key, double x1, double z1, double x2, double z2) {
        return Rectangle.of(key, x1, z1, x2, z2);
    }

    /**
     * Create a new rectangle.
     *
     * @param key    identifying key
     * @param point1 first point
     * @param point2 second point
     * @return a new rectangle
     */
    @NonNull
    public static Rectangle rectangle(@NonNull String key, @NonNull Point point1, @NonNull Point point2) {
        return Rectangle.of(key, point1, point2);
    }

    /**
     * Get the type identifier of this marker.
     * <p>
     * Used in the serialized json for the frontend.
     *
     * @return marker type
     */
    @NonNull
    public String getType() {
        return this.type;
    }

    /**
     * Get the map pane where the marker will be added.
     * <p>
     * Defaults to the overlay pane if null.
     *
     * @return map pane
     */
    @Nullable
    public String getPane() {
        return this.pane;
    }

    /**
     * Set the map pane where the marker will be added.
     * <p>
     * If the pane does not exist, it will be created the first time it is used.
     * <p>
     * Defaults to the overlay pane if null.
     *
     * @param pane map pane
     * @return this marker
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public T setPane(@Nullable String pane) {
        this.pane = pane;
        return (T) this;
    }

    /**
     * Get the options of this marker.
     * <p>
     * Null options represents "default" values. See wiki about defaults.
     *
     * @return marker options
     */
    @Nullable
    public Options getOptions() {
        return this.options;
    }

    /**
     * Set new options for this marker.
     * <p>
     * Null options represents "default" values. See wiki about defaults.
     *
     * @param options new options or null
     * @return this marker
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public T setOptions(@Nullable Options options) {
        this.options = options;
        return (T) this;
    }

    /**
     * Set new options for this marker.
     * <p>
     * Null options represents "default" values. See wiki about defaults.
     *
     * @param builder new options builder or null
     * @return this marker
     */
    @NonNull
    public T setOptions(Options.@Nullable Builder builder) {
        return setOptions(builder == null ? null : builder.build());
    }
}