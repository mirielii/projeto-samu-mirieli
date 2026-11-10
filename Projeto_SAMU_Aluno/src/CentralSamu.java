import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Central de regulação do SAMU. Recebe os chamados, mantém a fila de
 * espera, despacha veículos, escolhe o hospital de destino e registra as
 * estatísticas de desempenho do atendimento.
 *
 * Regras de despacho:
 *  - a fila é atendida por prioridade: maior gravidade primeiro e, em caso
 *    de empate, o chamado mais antigo primeiro;
 *  - é escolhido o veículo livre e capaz MAIS PRÓXIMO do paciente;
 *  - casos GRAVES só podem ser atendidos por UTI Móvel;
 *  - UTIs Móveis só atendem casos não graves se não houver ambulância básica livre;
 *  - chamados que esperam mais de TEMPO_MAXIMO_ESPERA etapas são perdidos;
 *  - o paciente é levado ao hospital mais próximo com leito livre; se o
 *    hospital estiver lotado na chegada, o veículo é redirecionado.
 */
public class CentralSamu implements Ator
{
    public static final int QTD_AMBULANCIAS_BASICAS_PADRAO = 4;
    public static final int QTD_UTIS_MOVEIS_PADRAO = 2;
    public static final int TEMPO_MAXIMO_ESPERA = 30;

    private Cidade cidade;
    private List<AmbulanciaBasica> ambulanciasBasicas;
    private List<UTIMovel> utisMoveis;
    private List<Hospital> hospitais;
    private List<Paciente> filaDeEspera;
    private Map<Object, Paciente> designacoes;
    private Estatisticas estatisticas;
    private int etapaAtual;

    public CentralSamu(Cidade cidade)
    {
        this(cidade, QTD_AMBULANCIAS_BASICAS_PADRAO, QTD_UTIS_MOVEIS_PADRAO);
    }

    public CentralSamu(Cidade cidade, int qtdBasicas, int qtdUtis)
    {
        this.cidade = cidade;
        ambulanciasBasicas = new ArrayList<AmbulanciaBasica>();
        utisMoveis = new ArrayList<UTIMovel>();
        hospitais = new ArrayList<Hospital>();
        filaDeEspera = new ArrayList<Paciente>();
        designacoes = new HashMap<Object, Paciente>();
        estatisticas = new Estatisticas();
        etapaAtual = 0;
        criaHospitais();
        criaVeiculos(qtdBasicas, qtdUtis);
    }

    /**
     * A cada etapa: avança o relógio, descarta os chamados que esperaram
     * demais e tenta despachar os chamados que estão na fila.
     */
    public void age()
{
    etapaAtual++;
    descartaChamadosExpirados();
    despachaChamados();
}

    /**
     * Recebe um novo chamado: o paciente entra na fila e a central tenta
     * despachar um veículo imediatamente.
     */
    public void solicitaAtendimento(Paciente paciente)
    {
        estatisticas.registraChamado(paciente.getGravidade());
        filaDeEspera.add(paciente);
        despachaChamados();
    }

    /**
     * Notificação de uma ambulância básica que chegou ao local de busca.
     */
    public void chegouAoLocal(AmbulanciaBasica ambulancia)
    {
        Paciente paciente = designacoes.remove(ambulancia);
        if(paciente == null) {
            throw new PacienteAusenteException(ambulancia);
        }
        estatisticas.registraTempoResposta(paciente.getGravidade(),
                                           paciente.getTempoDeEspera(etapaAtual));
        Hospital hospital = hospitalMaisProximo(ambulancia.getLocalizacao());
        ambulancia.embarca(paciente, hospital.getLocalizacao());
    }

    /**
     * Notificação de uma UTI móvel que chegou ao local de busca.
     */
    public void chegouAoLocal(UTIMovel uti)
    {
        Paciente paciente = designacoes.remove(uti);
        if(paciente == null) {
            throw new PacienteAusenteException(uti);
        }
        cidade.removeItem(paciente);
        estatisticas.registraTempoResposta(paciente.getGravidade(),
                                           paciente.getTempoDeEspera(etapaAtual));
        Hospital hospital = hospitalMaisProximo(uti.getLocalizacao());
        uti.embarca(paciente, hospital.getLocalizacao());
    }

    /**
     * Notificação de uma ambulância básica que chegou ao hospital com um paciente.
     */
    public void chegouAoHospital(AmbulanciaBasica ambulancia, Paciente paciente)
    {
        Hospital hospital = hospitalNaLocalizacao(ambulancia.getLocalizacao());
        hospital.interna(paciente.getGravidade());
        estatisticas.registraAtendimentoConcluido();
        ambulancia.desembarcaPaciente();
    }

    /**
     * Notificação de uma UTI móvel que chegou ao hospital com um paciente.
     */
    public void chegouAoHospital(UTIMovel uti, Paciente paciente)
    {
        Hospital hospital = hospitalNaLocalizacao(uti.getLocalizacao());
        hospital.interna(paciente.getGravidade());
        estatisticas.registraAtendimentoConcluido();
        uti.desembarcaPaciente();
    }

    public void adicionaAmbulanciaBasica(AmbulanciaBasica ambulancia)
    {
        ambulanciasBasicas.add(ambulancia);
        cidade.adicionaItem(ambulancia);
    }

    public void adicionaUTIMovel(UTIMovel uti)
    {
        utisMoveis.add(uti);
        cidade.adicionaItem(uti);
    }

    /**
     * Tenta despachar os chamados da fila, do mais prioritário para o menos
     * prioritário.
     */
    private void despachaChamados()
    {
        if(filaDeEspera.size() > 0) {
            Paciente paciente = filaDeEspera.get(0);
            Object veiculo = escolheVeiculo(paciente);
            if(veiculo != null) {
                filaDeEspera.remove(0);
                designacoes.put(veiculo, paciente);
                if(veiculo instanceof AmbulanciaBasica) {
                    ((AmbulanciaBasica) veiculo).setLocalDeBusca(paciente.getLocalizacao());
                }
                else {
                    ((UTIMovel) veiculo).setLocalDeBusca(paciente.getLocalizacao());
                }
            }
        }
    }

    /**
     * Escolhe o veículo livre e capaz mais próximo do paciente.
     * @return O veículo escolhido ou null se nenhum estiver disponível.
     */
    private Object escolheVeiculo(Paciente paciente)
    {
        for(AmbulanciaBasica ambulancia : ambulanciasBasicas) {
            if(ambulancia.estaLivre() && ambulancia.podeAtender(paciente.getGravidade())) {
                return ambulancia;
            }
        }
        for(UTIMovel uti : utisMoveis) {
            if(uti.estaLivre()) {
                return uti;
            }
        }
        return null;
    }

    /**
     * Remove da fila os chamados que esperaram mais que TEMPO_MAXIMO_ESPERA.
     */
    private void descartaChamadosExpirados()
    {
        for(int i = filaDeEspera.size() - 1; i >= 0; i--) {
            Paciente paciente = filaDeEspera.get(i);
            if(paciente.getTempoDeEspera(etapaAtual) > TEMPO_MAXIMO_ESPERA) {
                filaDeEspera.remove(i);
                estatisticas.registraChamadoPerdido(paciente.getGravidade());
            }
        }
    }

    /**
     * @return O hospital mais próximo da origem que tenha leito livre.
     */
    private Hospital hospitalMaisProximo(Localizacao origem)
    {
        Hospital maisProximo = null;
        int menorDistancia = Integer.MAX_VALUE;
        for(Hospital hospital : hospitais) {
            int distancia = origem.distancia(hospital.getLocalizacao());
            if(distancia < menorDistancia) {
                menorDistancia = distancia;
                maisProximo = hospital;
            }
        }
        return maisProximo;
    }

    /**
     * @return O hospital que está na localização informada (ou null).
     */
    private Hospital hospitalNaLocalizacao(Localizacao local)
    {
        for(Hospital hospital : hospitais) {
            if(hospital.getLocalizacao().equals(local)) {
                return hospital;
            }
        }
        return null;
    }

    private void criaHospitais()
    {
        int largura = cidade.getLargura();
        int altura = cidade.getAltura();
        hospitais.add(new Hospital("Hospital Universitário", largura / 4, altura / 4, 8));
        hospitais.add(new Hospital("Hospital de Caridade", 3 * largura / 4, altura / 2, 6));
        hospitais.add(new Hospital("Pronto Atendimento Central", largura / 3, 3 * altura / 4, 4));
        for(Hospital hospital : hospitais) {
            cidade.adicionaItem(hospital);
        }
    }

    private void criaVeiculos(int qtdBasicas, int qtdUtis)
    {
        Random rand = Randomizador.getRandom();
        for(int i = 0; i < qtdBasicas; i++) {
            Localizacao local = new Localizacao(rand.nextInt(cidade.getLargura()),
                                                rand.nextInt(cidade.getAltura()));
            adicionaAmbulanciaBasica(new AmbulanciaBasica(this, local));
        }
        for(int i = 0; i < qtdUtis; i++) {
            Localizacao local = new Localizacao(rand.nextInt(cidade.getLargura()),
                                                rand.nextInt(cidade.getAltura()));
            adicionaUTIMovel(new UTIMovel(this, local));
        }
    }

    public List<AmbulanciaBasica> getAmbulanciasBasicas()
    {
        return ambulanciasBasicas;
    }

    public List<UTIMovel> getUtisMoveis()
    {
        return utisMoveis;
    }

    public List<Hospital> getHospitais()
    {
        return hospitais;
    }

    public int getTamanhoFila()
    {
        return filaDeEspera.size();
    }

    public int getQuantidadeEmAtendimento()
    {
        int quantidade = designacoes.size();
        for(AmbulanciaBasica ambulancia : ambulanciasBasicas) {
            if(ambulancia.getPaciente() != null) {
                quantidade++;
            }
        }
        for(UTIMovel uti : utisMoveis) {
            if(uti.getPaciente() != null) {
                quantidade++;
            }
        }
        return quantidade;
    }

    public Estatisticas getEstatisticas()
    {
        return estatisticas;
    }

    public int getEtapaAtual()
    {
        return etapaAtual;
    }

    public Cidade getCidade()
    {
        return cidade;
    }
}
