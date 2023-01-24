public class Container {
    String data;
    Container next;

    public Container() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Node{");
        sb.append("data='").append(data).append('\'');
        sb.append(", next=").append(next);

        sb.append('}');
        return sb.toString();
    }
}