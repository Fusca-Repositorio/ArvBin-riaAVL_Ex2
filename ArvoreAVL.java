package atividade;

class ArvoreAVL {

	private Nodo raiz;

	
	
	// Método para obter a altura de um nó
	private int obterAltura(Nodo no) {
		if (no == null)
			return 0;
		return no.getAltura();
	}

	// Método para obter o fator de balanceamento de um nó
	private int obterFatorBalanceamento(Nodo no) {
		if (no == null)
			return 0;
		return obterAltura(no.getEsquerdo()) - obterAltura(no.getDireito());
	}

	// Rotação para a direita
	private Nodo rotacaoDireita(Nodo y) {
		Nodo x = y.getEsquerdo();
		Nodo T2 = x.getDireito();

		// Realiza rotação
		x.setDireito(y);
		y.setEsquerdo(T2);

		// Atualiza alturas
		y.setAltura(Math.max(obterAltura(y.getEsquerdo()), obterAltura(y.getDireito())) + 1);
		x.setAltura(Math.max(obterAltura(x.getEsquerdo()), obterAltura(x.getDireito())) + 1);

		// Retorna nova raiz
		return x;
	}

	// Rotação para a esquerda
	private Nodo rotacaoEsquerda(Nodo x) {
		Nodo y = x.getDireito();
		Nodo T2 = y.getEsquerdo();

		// Realiza rotação
		y.setEsquerdo(x);
		x.setDireito(T2);

		// Atualiza alturas
		x.setAltura(Math.max(obterAltura(x.getEsquerdo()), obterAltura(x.getDireito())) + 1);
		y.setAltura(Math.max(obterAltura(y.getEsquerdo()), obterAltura(y.getDireito())) + 1);

		// Retorna nova raiz
		return y;
	}

	// Inserção de um valor na árvore AVL
	public void inserir(int chave) {
		raiz = inserirNo(raiz, chave);
	}

	private Nodo inserirNo(Nodo no, int chave) {
		// Passo 1: Inserção normal na árvore binária de busca
		if (no == null)
			return (new Nodo(chave));

		if (chave < no.chave)
			no.setEsquerdo(inserirNo(no.getEsquerdo(), chave));
		else if (chave > no.chave)
			no.setDireito(inserirNo(no.getDireito(), chave));
		else
			return no;

		// Passo 2: Atualiza a altura deste nó ancestral
		no.setAltura(1 + Math.max(obterAltura(no.getEsquerdo()), obterAltura(no.getDireito())));

		// Passo 3: Obtém o fator de balanceamento deste nó ancestral
		int fatorBalanceamento = obterFatorBalanceamento(no);

		// Passo 4: Se o nó se tornar desbalanceado, há 4 casos

		// Caso de rotação à direita
		if (fatorBalanceamento > 1 && chave < no.getEsquerdo().chave) {
			System.out.println("Realizando rotação simples à direita");
			return rotacaoDireita(no);
		}

		// Caso de rotação à esquerda
		if (fatorBalanceamento < -1 && chave > no.getDireito().chave) {
			System.out.println("Realizando rotação simples à esquerda");
			return rotacaoEsquerda(no);
		}

		// Caso de rotação dupla à esquerda
		if (fatorBalanceamento > 1 && chave > no.getEsquerdo().chave) {
			System.out.println("Realizando rotação dupla à esquerda");
			no.setEsquerdo(rotacaoEsquerda(no.getEsquerdo()));
			return rotacaoDireita(no);
		}

		// Caso de rotação dupla à direita
		if (fatorBalanceamento < -1 && chave < no.getDireito().chave) {
			System.out.println("Realizando rotação dupla à direita");
			no.setDireito(rotacaoDireita(no.getDireito()));
			return rotacaoEsquerda(no);
		}

		return no;
	}

	// Método para imprimir a árvore
	public void imprimirArvore() {
		imprimirArvore(raiz, "", true);
	}

	private void imprimirArvore(Nodo no, String indentacao, boolean ultimo) {
		if (no != null) {
			System.out.print(indentacao);
			if (ultimo) {
				System.out.print("R----");
				indentacao += "   ";
			} else {
				System.out.print("L----");
				indentacao += "|  ";
			}
			System.out.println(no.chave + "(Altura:" + no.getAltura() + " FB:" + obterFatorBalanceamento(no) + ")");
			imprimirArvore(no.getEsquerdo(), indentacao, false);
			imprimirArvore(no.getDireito(), indentacao, true);
		}
	}

	// Método para pesquisar um valor na árvore
	public boolean pesquisar(int chave) {
		return pesquisarNo(raiz, chave) != null;
	}

	private Nodo pesquisarNo(Nodo no, int chave) {
		if (no == null || no.chave == chave)
			return no;

		if (no.chave > chave)
			return pesquisarNo(no.getEsquerdo(), chave);

		return pesquisarNo(no.getDireito(), chave);
	}

	// Método para encontrar o maior valor
	public int encontrarMaior() {
		return encontrarMaiorNo(raiz).chave;
	}

	private Nodo encontrarMaiorNo(Nodo no) {
		if (no.getDireito() == null)
			return no;
		return encontrarMaiorNo(no.getDireito());
	}

	// Método para encontrar o menor valor
	public int encontrarMenor() {
		return encontrarMenorNo(raiz).chave;
	}

	private Nodo encontrarMenorNo(Nodo no) {
		if (no.getEsquerdo() == null)
			return no;
		return encontrarMenorNo(no.getEsquerdo());
	}

	// Método para remover um valor da árvore AVL
	public void remover(int chave) {
		raiz = removerNo(raiz, chave);
	}

	private Nodo removerNo(Nodo no, int chave) {
		// Passo 1: Realiza a remoção normal na árvore binária de busca
		if (no == null)
			return no;

		if (chave < no.chave)
			no.setEsquerdo(removerNo(no.getEsquerdo(), chave));
		else if (chave > no.chave)
			no.setDireito(removerNo(no.getDireito(), chave));
		else {
			if ((no.getEsquerdo() == null) || (no.getDireito() == null)) {
				Nodo temp = null;
				if (temp == no.getEsquerdo())
					temp = no.getDireito();
				else
					temp = no.getEsquerdo();

				if (temp == null) {
					temp = no;
					no = null;
				} else
					no = temp;
			} else {
				Nodo temp = encontrarMenorNo(no.getDireito());
				no.chave = temp.chave;
				no.setDireito(removerNo(no.getDireito(), temp.chave));
			}
		}

		if (no == null)
			return no;

		// Passo 2: Atualiza a altura do nó atual
		no.setAltura(Math.max(obterAltura(no.getEsquerdo()), obterAltura(no.getDireito())) + 1);

		// Passo 3: Obtém o fator de balanceamento do nó atual
		int fatorBalanceamento = obterFatorBalanceamento(no);

		// Passo 4: Se o nó estiver desbalanceado, há 4 casos

		// Caso de rotação à direita
		if (fatorBalanceamento > 1 && obterFatorBalanceamento(no.getEsquerdo()) >= 0)
			return rotacaoDireita(no);

		// Caso de rotação dupla à esquerda
		if (fatorBalanceamento > 1 && obterFatorBalanceamento(no.getEsquerdo()) < 0) {
			no.setEsquerdo(rotacaoEsquerda(no.getEsquerdo()));
			return rotacaoDireita(no);
		}

		// Caso de rotação à esquerda
		if (fatorBalanceamento < -1 && obterFatorBalanceamento(no.getDireito()) <= 0)
			return rotacaoEsquerda(no);

		// Caso de rotação dupla à direita
		if (fatorBalanceamento < -1 && obterFatorBalanceamento(no.getDireito()) > 0) {
			no.setDireito(rotacaoDireita(no.getDireito()));
			return rotacaoEsquerda(no);
		}

		return no;
	}
}