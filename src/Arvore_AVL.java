public class Arvore_AVL {
	public Nodo raiz;

	public Arvore_AVL() {
		this.raiz = null;
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}

	// Função de rotação simples à direita
	public Nodo rotacaoDireita(Nodo aux2) {
		Nodo aux = aux2.getEsquerdo();
		Nodo T2 = aux.getDireito();
		// Realiza rotação
		aux.setDireito(aux2);
		aux2.setEsquerdo(T2); // Atualiza alturas
		aux2.setAltura(Math.max(calculaAltura(aux2.getEsquerdo()), calculaAltura(aux2.getDireito())) + 1);
		aux.setAltura(Math.max(calculaAltura(aux.getEsquerdo()), calculaAltura(aux.getDireito())) + 1);
		// Retorna nova raiz
		return aux;
	}

// Função de rotação simples à esquerda 
	public Nodo rotacaoEsquerda(Nodo aux) {
		Nodo aux2 = aux.getDireito();
		Nodo T2 = aux2.getEsquerdo(); // Realiza rotação
		aux2.setEsquerdo(aux);
		aux.setDireito(T2); // Atualiza alturas
		aux.setAltura(Math.max(calculaAltura(aux.getEsquerdo()), calculaAltura(aux.getDireito())) + 1);
		aux2.setAltura(Math.max(calculaAltura(aux2.getEsquerdo()), calculaAltura(aux2.getDireito())) + 1);
		return aux2;
	}// Retorna nova raiz

	// Função de rotação direita-esquerda
	public Nodo rotacaoDireitaEsquerda(Nodo aux) {
		aux.setDireito(rotacaoDireita(aux.getDireito()));
		return rotacaoEsquerda(aux);
	} // Função de rotação esquerda-direita

	public Nodo rotacaoEsquerdaDireita(Nodo aux) {
		aux.setEsquerdo(rotacaoEsquerda(aux.getEsquerdo()));
		return rotacaoDireita(aux);
	} // Função para verificar e balancear a árvore

	public void insere(Nodo aux, int valor) {
		// verifica se a raiz da árvore não é nula
		if (aux != null) {
			Nodo novo = new Nodo(valor);
			// Verifica se o valor a ser inserido é menor que o nodo corrente da árvore, se
			// sim, vai para subárvore esquerda
			if (valor < aux.getValor()) {
				// Se tiver elemento no nodo esquerdo, continua a busca
				if (aux.getEsquerdo() != null)
					insere(aux.getEsquerdo(), valor);
				else {
					// Se nodo esquerdo vazio, insere o novo nodo aqui
					System.out.println("Inserindo " + valor + " a esquerda de " + aux.getValor());
					aux.setEsquerdo(novo);
				}
				// Verifica se o valor a ser inserido é maior que o nodo corrente da árvore, se
				// sim, vai para subárvore direita
			} else if (valor > aux.getValor()) {
				// Se tiver elemento no nodo direito, continua a busca
				if (aux.getDireito() != null) {
					insere(aux.getDireito(), valor);
				} else {
					// Se nodo direito vazio, insere o novo nodo aqui
					System.out.println("Inserindo " + valor + " a direita de " + aux.getValor());
					aux.setDireito(novo);
				}
			}
			novo.setPai(aux);
			calculaAltura(aux);
			System.out.println("*******Verifica balanceamento de  " + aux.getValor());
			verificaBalanceamento(aux);
		} else {
			System.out.println("Inserindo " + valor + " na raiz");
			setRaiz(new Nodo(valor));
		}
	}

// Função Principal de Remoção
	public void remove(int valor) {
		raiz = removeRecursivo(raiz, valor);
	}

	// Função Recursiva de Remoção
	private Nodo removeRecursivo(Nodo aux, int valor) {
		if (aux == null) {
			System.out.println("Valor não encontrado!");
			return null;
		}

		if (valor < aux.getValor()) {
			aux.setEsquerdo(removeRecursivo(aux.getEsquerdo(), valor));
		} else if (valor > aux.getValor()) {
			aux.setDireito(removeRecursivo(aux.getDireito(), valor));
		} else {
			aux = lidaRemocao(aux);
		}
		return aux;
	}

	// Função de Tratamento de Remoção
	private Nodo lidaRemocao(Nodo aux) {
		if (aux.getEsquerdo() == null && aux.getDireito() == null) {
			return null; // Nó folha
		} else if (aux.getEsquerdo() == null) {
			return aux.getDireito(); // Nó com subárvore direita
		} else if (aux.getDireito() == null) {
			return aux.getEsquerdo(); // Nó com subárvore esquerda
		} else {
			Nodo substituto = encontraMinimo(aux.getDireito());
			aux.setValor(substituto.getValor());
			aux.setDireito(removeRecursivo(aux.getDireito(), substituto.getValor()));
			return aux;
		}
	}

	// Função para Encontrar o Mínimo
	public Nodo encontraMinimo(Nodo aux) {
		while (aux.getEsquerdo() != null) {
			aux = aux.getEsquerdo();
		}
		return aux;
	}

	public Nodo encontraMaximo(Nodo aux) {
		while (aux.getDireito() != null) {
			aux = aux.getDireito();
		}
		return aux;
	}
	
	private int calculaAltura(Nodo aux) {
		if (aux == null)
			return 0;
		return aux.getAltura();
	}

	private void verificaBalanceamento(Nodo aux) {
		if (aux.getFatorBalanceamento() == -2) {
			if (aux.getEsquerdo().getFatorBalanceamento() < 0)
				rotacaoDireita(aux);
			else
				rotacaoEsquerdaDireita(aux);
		} else {
			if (aux.getFatorBalanceamento() == 2) {
				if (aux.getDireito().getFatorBalanceamento() > 0)
					rotacaoEsquerda(aux);
				else
					rotacaoDireitaEsquerda(aux);
			}
		}
	}

	// Função única de Impressão
	public void imprime(Nodo aux) {
		if (aux != null) {
			System.out.println(aux.getValor());
			imprime(aux.getEsquerdo());
			imprime(aux.getDireito());
		}
	}

	// Função Principal de Procura
	public boolean procura(int valor) {
		return procuraRecursivo(raiz, valor);
	}

	// Função Recursiva de Procura
	private boolean procuraRecursivo(Nodo aux, int valor) {
		if (aux == null) {
			return false;
		}
		if (valor == aux.getValor()) {
			return true;
		} else if (valor < aux.getValor()) {
			return procuraRecursivo(aux.getEsquerdo(), valor);
		} else {
			return procuraRecursivo(aux.getDireito(), valor);
		}
	}
}
