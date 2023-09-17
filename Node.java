public class Node {
    int dado;
    Node proximo;

    public Node(int dado) {
        this.dado = dado;
        this.proximo = null;
    }
    public int getDado(){
        return dado;
    }
    public void setDado(int dado){
        this.dado = dado;
    }
    public Node getProximo(){
        return proximo;
    }
}