package rectangle;

@FunctionalInterface
public interface RectangleClick<C> {
    public boolean apply(C c);
}
