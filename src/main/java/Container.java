public class Container {
    String data;
    Container next;

    public Container(String data) {
    }

    public Container() {
    }

    public String getData() {
        return data;
    }

    public Container getNext() {
        return next;
    }

    public void setData(String data) {
        this.data = data;
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