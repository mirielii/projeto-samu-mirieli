import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de unidade da CentralSamu, baseados nas regras da Seção 5 do enunciado.
 *
 * Cenário base: cidade 30x30 e central SEM veículos (0 básicas, 0 UTIs). Os veículos
 * são adicionados em cada teste, em posições conhecidas, para o teste ser determinístico.
 * Hospitais (fixos pela central):
 *   [0] Universitário (7,7) 8 leitos | [1] Caridade (22,15) 6 leitos | [2] PA Central (10,22) 4 leitos
 *
 * Os veículos são chamados diretamente (sem Simulacao), então a central é testada isolada.
 */
public class CentralSamuTest
{
    private Cidade cidade;
    private CentralSamu central;

    @BeforeEach
    public void inicializacao()
    {
        Randomizador.reinicia();
        cidade = new Cidade();
        central = new CentralSamu(cidade, 0, 0);
    }

    // ---------- métodos auxiliares ----------

    private Paciente paciente(int x, int y, int gravidade, double instante)
    {
        return new Paciente(new Localizacao(x, y), gravidade, instante);
    }

    private AmbulanciaBasica basica(int x, int y)
    {
        AmbulanciaBasica a = new AmbulanciaBasica(central, new Localizacao(x, y));
        central.adicionaAmbulanciaBasica(a);
        return a;
    }

    private UTIMovel uti(int x, int y)
    {
        UTIMovel u = new UTIMovel(central, new Localizacao(x, y));
        central.adicionaUTIMovel(u);
        return u;
    }

    private void avancaEtapas(int n)
    {
        for(int i = 0; i < n; i++) {
            central.age();
        }
    }

    private void lotaHospital(Hospital h)
    {
        for(int i = 0; i < h.getCapacidade(); i++) {
            h.interna(Gravidade.LEVE);
        }
    }

    // ---------- construção ----------

    @Test
    public void construtorPadraoCriaFrotaPadrao()
    {
        CentralSamu c = new CentralSamu(new Cidade());
        assertEquals(4, c.getAmbulanciasBasicas().size());
        assertEquals(2, c.getUtisMoveis().size());
    }

    @Test
    public void criaTresHospitaisComCapacidadesDoEnunciado()
    {
        assertEquals(3, central.getHospitais().size());
        assertEquals(8, central.getHospitais().get(0).getCapacidade());
        assertEquals(6, central.getHospitais().get(1).getCapacidade());
        assertEquals(4, central.getHospitais().get(2).getCapacidade());
    }

    @Test
    public void hospitaisEstaoNaCidade()
    {
        for(Hospital h : central.getHospitais()) {
            assertTrue(cidade.contem(h));
        }
    }

    @Test
    public void veiculosIniciaisFicamDentroDosLimitesDaCidade()
    {
        CentralSamu c = new CentralSamu(new Cidade(), 10, 6);
        for(AmbulanciaBasica a : c.getAmbulanciasBasicas()) {
            assertTrue(a.getLocalizacao().getX() < Cidade.LARGURA_PADRAO);
            assertTrue(a.getLocalizacao().getY() < Cidade.ALTURA_PADRAO);
        }
        for(UTIMovel u : c.getUtisMoveis()) {
            assertTrue(u.getLocalizacao().getX() < Cidade.LARGURA_PADRAO);
            assertTrue(u.getLocalizacao().getY() < Cidade.ALTURA_PADRAO);
        }
    }

    // ---------- relógio e registro de chamados ----------

    @Test
    public void ageAvancaORelogioEmUmaEtapa()
    {
        central.age();
        assertEquals(1, central.getEtapaAtual());
    }

    @Test
    public void solicitaAtendimentoRegistraChamadoNasEstatisticas()
    {
        central.solicitaAtendimento(paciente(5, 5, Gravidade.LEVE, 0));
        assertEquals(1, central.getEstatisticas().getTotalChamados());
    }

    @Test
    public void chamadoSemVeiculoDisponivelFicaNaFila()
    {
        central.solicitaAtendimento(paciente(5, 5, Gravidade.LEVE, 0));
        assertEquals(1, central.getTamanhoFila());
    }

    // ---------- escolha do veículo ----------

    @Test
    public void chamadoGraveNaoEhDespachadoParaAmbulanciaBasica()
    {
        AmbulanciaBasica b = basica(0, 0);
        central.solicitaAtendimento(paciente(1, 1, Gravidade.GRAVE, 0));
        assertTrue(b.estaLivre());
        assertEquals(1, central.getTamanhoFila());
    }

    @Test
    public void chamadoGraveEhDespachadoParaUTI()
    {
        UTIMovel u = uti(0, 0);
        Paciente p = paciente(1, 1, Gravidade.GRAVE, 0);
        central.solicitaAtendimento(p);
        assertEquals(p.getLocalizacao(), u.getDestino());
    }

    @Test
    public void chamadoLeveUsaBasicaMesmoComUTIMaisProxima()
    {
        AmbulanciaBasica b = basica(29, 29);
        UTIMovel u = uti(1, 1);
        Paciente p = paciente(0, 0, Gravidade.LEVE, 0);
        central.solicitaAtendimento(p);
        assertEquals(p.getLocalizacao(), b.getDestino());
        assertTrue(u.estaLivre());
    }

    @Test
    public void chamadoNaoGraveUsaUTISeNaoHaBasicaLivre()
    {
        UTIMovel u = uti(0, 0);
        Paciente p = paciente(3, 3, Gravidade.MODERADO, 0);
        central.solicitaAtendimento(p);
        assertEquals(p.getLocalizacao(), u.getDestino());
    }

    @Test
    public void chamadoNaoGraveUsaUTISeBasicasEstaoOcupadas()
    {
        AmbulanciaBasica b = basica(0, 0);
        b.setLocalDeBusca(new Localizacao(9, 9)); // básica ocupada
        UTIMovel u = uti(5, 5);
        Paciente p = paciente(3, 3, Gravidade.LEVE, 0);