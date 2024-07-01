package atividade;

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
	public Nodo rotacaoDireita(Nodo aux) {
		Nodo auxMaior = aux;
		Nodo auxMedio = aux.getEsquerdo();
		aux = aux.getPai();
		// Realiza rotação
		auxMaior.setEsquerdo(auxMedio.getDireito());
		auxMedio.setDireito(auxMaior);
		auxMaior.setPai(auxMedio);
		auxMedio.setPai(aux);
		if (auxMedio.getPai()==null) {
			raiz = auxMedio;
		}else {
			aux.setDireito(auxMedio);
		}
		calculaAltura(auxMaior);
		calculaAltura(auxMedio);
		return aux;
	}

// Função de rotação simples à esquerda 
	public Nodo rotacaoEsquerda(Nodo aux) {
		Nodo auxMenor = aux;
		Nodo auxMedio = aux.getDireito();
		aux = aux.getPai();
		// Realiza rotação
		auxMenor.setDireito(auxMedio.getEsquerdo());
		auxMedio.setEsquerdo(auxMenor);
		auxMenor.setPai(auxMedio);
		auxMedio.setPai(aux);
		if (auxMedio.getPai()==null) {
			raiz = auxMedio;
		}else {
			aux.setEsquerdo(auxMedio);
		}
		calculaAltura(auxMedio);
		calculaAltura(auxMenor);
		return aux;
	}

	// Função de rotação direita-esquerda
	public Nodo rotacaoDireitaEsquerda(Nodo aux) {
		rotacaoDireita(aux);
		rotacaoEsquerda(aux);
		return rotacaoEsquerda(aux);
	} // Função de rotação esquerda-direita

	public Nodo rotacaoEsquerdaDireita(Nodo aux) {
		rotacaoEsquerda(aux);
		rotacaoDireita(aux);
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
			System.out.println("*****Verifica balanceamento de  " + aux.getValor()+"*****");
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
		if(aux == null)
			return 0;
		else {
			int esq = calculaAltura(aux.getEsquerdo());
			int dir = calculaAltura(aux.getDireito());
			aux.setFatorBalanceamento(dir-esq);		
			if(esq > dir) {
				aux.setAltura(esq);
				return esq+1;
			}
			else  {
				aux.setAltura(dir);
				return dir+1;
			}
		}
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
//	public void imprime(Nodo aux) {
//		if (aux != null) {
//			System.out.println(aux.getValor());
//			imprime(aux.getEsquerdo());
//			imprime(aux.getDireito());
//		}
//	}

	public void imprime(Nodo aux) {
		if(aux != null) {
			System.out.println("----- ");
			System.out.println(aux.getValor());
			if(aux.getPai() != null)
				System.out.println("pai: "+aux.getPai().getValor());
			System.out.println("altura: "+aux.getAltura());
			System.out.println("balanceamento: "+aux.getFatorBalanceamento());
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
