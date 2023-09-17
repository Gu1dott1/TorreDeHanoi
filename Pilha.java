import java.util.Random;
import java.util.Scanner;

public class Pilha {
    private Node topo;
    private int tamanho;
    private int tamanhoMaximo;
    private boolean ordemCrescente;

    public Pilha(int tamanhoMaximo, boolean ordemCrescente) {
        this.topo = null;
        this.tamanho = 0;
        this.tamanhoMaximo = tamanhoMaximo;
        this.ordemCrescente = ordemCrescente;
    }

    public void Remover() {
        if (Vazia()) {
            System.out.println("A sua pilha está vazia, por favor adicione algo antes de tentar remover.");
        } else {
            topo = topo.getProximo();
            tamanho--;
        }
    }

    public void Insere(int dado) {
        Node no = new Node(dado);
        no.proximo = topo;
        topo = no;
        tamanho++;
    }

    public static boolean verificar(Pilha pilhaOrigem, Pilha pilhaDestino) {
        if (pilhaOrigem.Vazia()) {
            return false;
        }
        if (pilhaDestino.Vazia() || (pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() < pilhaDestino.topo.getDado()) || (!pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() > pilhaDestino.topo.getDado())) {
            return true;
        }
        return false;
    }

    public void pop(Pilha pilhaOrigem) {
        if (pilhaOrigem.Vazia()) {
            System.out.println("A pilha está vazia. Não é possível remover o disco.");
        } else {
            pilhaOrigem.Remover();
        }
    }

    public static void push(Node numero, Pilha pilhaDestino) {
        if (pilhaDestino.cheia()) {
            System.out.println("A pilha está cheia. Não é possível inserir o disco.");
        } else {
            pilhaDestino.Insere(numero.getDado());
        }
    }

    public boolean cheia() {
        return tamanho == tamanhoMaximo;
    }

    public void Imprimir(int id) {
        Node atual = topo;
        System.out.println("Pilha " + id + "\n");

        while (atual != null) {
            System.out.println("| " + atual.dado + " |");
            atual = atual.getProximo();
        }

        System.out.println("\n");
    }

    public void Ordenar() {
        if (Vazia() || tamanho == 1) {
            return;
        }

        Pilha auxiliar = new Pilha(tamanhoMaximo, ordemCrescente);

        while (!Vazia()) {
            int valor = topo.dado;
            Remover();

            while (!auxiliar.Vazia() && (ordemCrescente ? auxiliar.topo.dado > valor : auxiliar.topo.dado < valor)) {
                Insere(auxiliar.topo.dado);
                auxiliar.Remover();
            }
            auxiliar.Insere(valor);
        }
        while (!auxiliar.Vazia()) {
            Insere(auxiliar.topo.dado);
            auxiliar.Remover();
        }
    }

    public boolean Vazia() {
        return topo == null;
    }

    public void resolverTorreHanoi(int n, Pilha origem, Pilha destino, Pilha auxiliar) {
        if (n == 1) {
            moverDisco(origem, destino);
        } else {
            resolverTorreHanoi(n - 1, origem, auxiliar, destino);
            moverDisco(origem, destino);
            resolverTorreHanoi(n - 1, auxiliar, destino, origem);
        }
    }


    public static boolean verificarVitoria(int tamanho, Pilha pilhaDestino, Pilha pilhaOrigem, Pilha pilhaAuxiliar) {
        // Verifica se a pilhaDestino está cheia
        if (pilhaDestino.tamanho != tamanho) {
            return false;
        }

        // Verifica se todos os discos estão na pilhaDestino
        while (!pilhaOrigem.Vazia()) {
            if ((pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() < pilhaDestino.topo.getDado()) || (!pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() > pilhaDestino.topo.getDado())) {
                pilhaDestino.Insere(pilhaOrigem.topo.getDado());
                pilhaOrigem.Remover();
            } else {
                return false;
            }
        }

        while (!pilhaAuxiliar.Vazia()) {
            if ((pilhaAuxiliar.ordemCrescente && pilhaAuxiliar.topo.getDado() < pilhaDestino.topo.getDado()) || (!pilhaAuxiliar.ordemCrescente && pilhaAuxiliar.topo.getDado() > pilhaDestino.topo.getDado())) {
                pilhaDestino.Insere(pilhaAuxiliar.topo.getDado());
                pilhaAuxiliar.Remover();
            } else {
                return false;
            }
        }

        // Verifica se a pilhaDestino agora está cheia com os discos
        return pilhaDestino.tamanho == tamanho;
    }

    public void moverDisco(Pilha origem, Pilha destino) {
        if (origem.Vazia()) {
            System.out.println("Pilha de origem vazia. Não é possível mover o disco.");
            return;
        }
    
        if (destino.Vazia()) {
            Node disco = origem.topo;
            origem.pop(origem);
            destino.push(disco, destino);
            return;
        }
    
        if (origem.ordemCrescente && origem.topo.getDado() < destino.topo.getDado()) {
            Node disco = origem.topo;
            origem.pop(origem);
            destino.push(disco, destino);
        } else if (!origem.ordemCrescente && origem.topo.getDado() > destino.topo.getDado()) {
            Node disco = origem.topo;
            origem.pop(origem);
            destino.push(disco, destino);
        } else {
            System.out.println("Movimento inválido. Não é possível mover o disco.");
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Bem-vindo ao temido jogo da Torre de Hanoi! Por favor, digite o tamanho das pilhas:");
        int tamanho = in.nextInt();
        System.out.println("Escolha a ordem dos discos:");
        System.out.println("1 - Ordem crescente");
        System.out.println("2 - Ordem decrescente");
        int escolhaOrdem = in.nextInt();
        boolean ordemCrescente = (escolhaOrdem == 1);

        Pilha pilha1 = new Pilha(tamanho, ordemCrescente);
        Pilha pilha2 = new Pilha(tamanho, ordemCrescente);
        Pilha pilha3 = new Pilha(tamanho, ordemCrescente);
        Pilha global = new Pilha(tamanho, ordemCrescente);

        for (int i = 0; i < tamanho; i++) {
            Random rand = new Random();
            int dado = rand.nextInt(100);
            pilha1.Insere(dado);
        }

        pilha1.Ordenar();

        pilha1.Imprimir(1);
        pilha2.Imprimir(2);
        pilha3.Imprimir(3);

        int contador = 0;
        boolean vitoria = false;

        while (!vitoria) {
            System.out.println("\n0- Sair do Jogo\n1- Movimentar\n2- Solução Automática");
            int opcao = in.nextInt();
            switch (opcao) {
                case 0:
                    System.out.println("Você saiu do jogo.");
                    return;
                case 1:
                    System.out.println("Qual pilha você deseja modificar?");
                    int movimento = in.nextInt();
                    switch (movimento) {
                        case 1:
                            System.out.println("Para qual pilha você deseja mover (2 ou 3)?");
                            int resultado = in.nextInt();
                            if (resultado == 2) {
                                pilha2.moverDisco(pilha1, pilha2);
                            } else if (resultado == 3) {
                                pilha3.moverDisco(pilha1, pilha3);
                            }
                            contador++;
                            break;
                        case 2:
                            System.out.println("Para qual pilha você deseja mover (1 ou 3)?");
                            resultado = in.nextInt();
                            if (resultado == 1) {
                                pilha1.moverDisco(pilha2, pilha1);
                            } else if (resultado == 3) {
                                pilha3.moverDisco(pilha2, pilha3);
                            }
                            contador++;
                            break;
                        case 3:
                            System.out.println("Para qual pilha você deseja mover (1 ou 2)?");
                            resultado = in.nextInt();
                            if (resultado == 1) {
                                pilha1.moverDisco(pilha3, pilha1);
                            } else if (resultado == 2) {
                                pilha2.moverDisco(pilha3, pilha2);
                            }
                            contador++;
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Resolvendo a Torre de Hanoi...");
                    Pilha auxiliar = new Pilha(tamanho, ordemCrescente);
                    pilha1.resolverTorreHanoi(tamanho, pilha1, pilha3, auxiliar);
                    System.out.println("Torre de Hanoi resolvida!");
                    contador += (int) (Math.pow(2, tamanho) - 1); 
                    break;
            }

            pilha1.Imprimir(1);
            pilha2.Imprimir(2);
            pilha3.Imprimir(3);


            vitoria = verificarVitoria(tamanho, pilha3, pilha1, pilha2);
        }

        System.out.printf("Você precisou de %d jogadas para ganhar!\n", contador);
    }
}