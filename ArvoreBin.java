import java.io.*;

public class ArvoreBin {
    private Nodo raiz;

    public ArvoreBin() {
        raiz = null;
    }

    public Nodo alocarNodo(int valor) {
        return new Nodo(valor);
    }

    public void inserir(int valor) {
        raiz = inserir(valor, raiz);
    }

    private Nodo inserir(int valor, Nodo raiz) {
        if (raiz == null) {
            raiz = alocarNodo(valor);
        } else {
            if (valor < raiz.info) {
                raiz.esq = inserir(valor, raiz.esq);
                raiz.esq.pai = raiz;
            } else if (valor > raiz.info) {
                raiz.dir = inserir(valor, raiz.dir);
                raiz.dir.pai = raiz;
            }
        }
        return raiz;
    }

    public void preOrdem() {
        preOrdem(raiz);
    }

    private void preOrdem(Nodo raiz) {
        if (raiz != null) {
            System.out.print(raiz.info + " ");
            preOrdem(raiz.esq);
            preOrdem(raiz.dir);
        }
    }

    public void central() {
        central(raiz);
    }

    private void central(Nodo raiz) {
        if (raiz != null) {
            central(raiz.esq);
            System.out.print(raiz.info + " ");
            central(raiz.dir);
        }
    }

    public void posOrdem() {
        posOrdem(raiz);
    }

    private void posOrdem(Nodo raiz) {
        if (raiz != null) {
            posOrdem(raiz.esq);
            posOrdem(raiz.dir);
            System.out.print(raiz.info + " ");
        }
    }

    public void remover(int valor) {
        raiz = remover(valor, raiz);
    }

    private Nodo remover(int valor, Nodo raiz) {
        if (raiz == null)
            return raiz;

        if (valor < raiz.info) {
            raiz.esq = remover(valor, raiz.esq);
        } else if (valor > raiz.info) {
            raiz.dir = remover(valor, raiz.dir);
        } else {
            if (raiz.esq == null) {
                raiz = raiz.dir;
            } else if (raiz.dir == null) {
                raiz = raiz.esq;
            } else {
                raiz.info = buscarMin(raiz.dir).info;
                raiz.dir = remover(raiz.info, raiz.dir);
            }
        }
        return raiz;
    }

    public Nodo buscar(int valor) {
        return buscar(valor, raiz);
    }

    private Nodo buscar(int valor, Nodo raiz) {
        if (raiz == null || raiz.info == valor) {
            return raiz;
        }

        if (valor < raiz.info) {
            return buscar(valor, raiz.esq);
        } else {
            return buscar(valor, raiz.dir);
        }
    }

    private Nodo buscarMin(Nodo raiz) {
        if (raiz == null) {
            return null;
        }

        while (raiz.esq != null) {
            raiz = raiz.esq;
        }

        return raiz;
    }

    public void gerarArqDot(String filename) {
        try {
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("digraph G {\n");
            escreverPreOrdemDot(raiz, out);
            out.write("}\n");
            out.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo DOT: " + e.getMessage());
        }
    }

    private void escreverPreOrdemDot(Nodo raiz, BufferedWriter out) throws IOException {
        if (raiz != null) {
            if (raiz.esq != null) {
                out.write(raiz.info + " -> " + raiz.esq.info + ";\n");
            }
            if (raiz.dir != null) {
                out.write(raiz.info + " -> " + raiz.dir.info + ";\n");
            }
            escreverPreOrdemDot(raiz.esq, out);
            escreverPreOrdemDot(raiz.dir, out);
        }
    }

    public static void main(String[] args) {

        ArvoreBin arvore = new ArvoreBin();

        arvore.inserir(10);
        arvore.inserir(5);
        arvore.inserir(20);
        arvore.inserir(3);
        arvore.inserir(7);

        // remoção de um nodo folha
        arvore.remover(7);

        arvore.gerarArqDot("arvoreBin.dot");

        ArvoreBin arvore2 = new ArvoreBin();

        arvore2.inserir(5);
        arvore2.inserir(3);
        arvore2.inserir(8);
        arvore2.inserir(2);
        arvore2.inserir(4);

        // remoção de um nodo pai
        arvore2.remover(8);

        arvore2.gerarArqDot("arvoreBin2.dot");

        ArvoreBin arvore3 = new ArvoreBin();

        arvore3.inserir(3);
        arvore3.inserir(6);
        arvore3.inserir(9);
        arvore3.inserir(12);
        arvore3.inserir(15);

        // buscar um valor na árvore
        arvore3.buscar(6);

        arvore3.gerarArqDot("arvoreBin3.dot");

        ArvoreBin arvore4 = new ArvoreBin();

        arvore4.inserir(10);
        arvore4.inserir(20);
        arvore4.inserir(19);
        arvore4.inserir(11);
        arvore4.inserir(13);

        // caminhamentos
        System.out.println("Caminhamento pré-ordem:");
        arvore4.preOrdem();

        System.out.println("\nCaminhamento em ordem:");
        arvore4.central();

        System.out.println("\nCaminhamento pós-ordem:");
        arvore4.posOrdem();

        arvore4.gerarArqDot("arvoreBin4.dot");
    }
}

class Nodo {
    int info;
    Nodo esq, dir, pai;

    public Nodo(int valor) {
        info = valor;
        esq = dir = pai = null;
    }
}
