public class Container {
    String data;
    Container next;

    public Container() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Container{");
        sb.append("data='").append(data).append('\'');
        sb.append(", next=").append(next);
        sb.append('}');
        return sb.toString();
    }
}