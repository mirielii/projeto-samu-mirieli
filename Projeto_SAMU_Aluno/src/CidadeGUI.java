import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Interface gráfica (Swing) da simulação. Mostra a grade da cidade com os
 * hospitais (verde, "H"), os pacientes aguardando socorro (amarelo = leve,
 * laranja = moderado, vermelho = grave), as ambulâncias básicas (azul) e as
 * UTIs móveis (roxo). Tons escuros indicam veículos transportando paciente.
 */
public class CidadeGUI extends JFrame implements Ator
{
    private static final int TAMANHO_CELULA = 18;
    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_GRADE = new Color(230, 230, 230);

    private final Cidade cidade;
    private final CentralSamu central;
    private final VisaoCidade visao;
    private final JLabel rotuloEtapa;
    private final JLabel rotuloEstatisticas;

    public CidadeGUI(Cidade cidade, CentralSamu central)
    {
        this.cidade = cidade;
        this.central = central;
        setTitle("Simulação SAMU - Central de Ambulâncias");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        rotuloEtapa = new JLabel("Etapa: 0", JLabel.CENTER);
        rotuloEstatisticas = new JLabel("<html>&nbsp;<br>&nbsp;</html>", JLabel.CENTER);
        JLabel legenda = new JLabel("<html><font color='#008c3c'>&#9632; Hospital</font> &nbsp; "
            + "<font color='#e6be00'>&#9632; Leve</font> &nbsp; "
            + "<font color='#ff7800'>&#9632; Moderado</font> &nbsp; "
            + "<font color='#d20000'>&#9632; Grave</font> &nbsp; "
            + "<font color='#508cff'>&#9632; Amb. Básica</font> &nbsp; "
            + "<font color='#c85ae6'>&#9632; UTI Móvel</font></html>", JLabel.CENTER);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(rotuloEtapa, BorderLayout.NORTH);
        norte.add(legenda, BorderLayout.SOUTH);

        visao = new VisaoCidade(cidade.getLargura(), cidade.getAltura());
        getContentPane().add(norte, BorderLayout.NORTH);
        getContentPane().add(visao, BorderLayout.CENTER);
        getContentPane().add(rotuloEstatisticas, BorderLayout.SOUTH);
        pack();
        setLocation(80, 40);
        setVisible(true);
        age();
    }

    /**
     * Redesenha a cidade e atualiza os rótulos com o estado atual.
     */
    public void age()
    {
        visao.preparaDesenho();
        List<ItemDesenhavel> veiculos = new ArrayList<ItemDesenhavel>();
        for(Object objeto : cidade.getItens()) {
            ItemDesenhavel item = (ItemDesenhavel) objeto;
            if(item instanceof AmbulanciaBasica || item instanceof UTIMovel) {
                veiculos.add(item);   // veículos são desenhados por último (por cima)
            }
            else {
                visao.desenhaItem(item, item instanceof Hospital);
            }
        }
        for(ItemDesenhavel veiculo : veiculos) {
            visao.desenhaItem(veiculo, false);
        }
        rotuloEtapa.setText("Etapa: " + central.getEtapaAtual()
                            + "    Chamados na fila: " + central.getTamanhoFila()
                            + "    Em atendimento: " + central.getQuantidadeEmAtendimento());
        Estatisticas e = central.getEstatisticas();
        rotuloEstatisticas.setText(String.format("<html><center>Chamados: %d &nbsp; Perdidos: %d (%.1f%%) &nbsp; "
            + "Concluídos: %d<br>Resposta média: %.1f &nbsp; Resposta grave: %.1f &nbsp; Metas: %s</center></html>",
            e.getTotalChamados(), e.getTotalPerdidos(), e.getTaxaPerda() * 100, e.getAtendimentosConcluidos(),
            e.getTempoMedioResposta(), e.getTempoMedioResposta(Gravidade.GRAVE),
            e.metasAtingidas() ? "ATINGIDAS" : "não atingidas"));
        visao.repaint();
    }

    /**
     * Painel que desenha a grade da cidade em uma imagem fora da tela.
     */
    private class VisaoCidade extends JPanel
    {
        private final int largura;
        private final int altura;
        private final BufferedImage imagem;
        private final Graphics2D g;

        public VisaoCidade(int largura, int altura)
        {
            this.largura = largura;
            this.altura = altura;
            imagem = new BufferedImage(largura * TAMANHO_CELULA, altura * TAMANHO_CELULA,
                                       BufferedImage.TYPE_INT_RGB);
            g = imagem.createGraphics();
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, TAMANHO_CELULA - 4));
        }

         public Dimension getPreferredSize()
        {
            return new Dimension(largura * TAMANHO_CELULA, altura * TAMANHO_CELULA);
        }

        public void preparaDesenho()
        {
            g.setColor(COR_FUNDO);
            g.fillRect(0, 0, imagem.getWidth(), imagem.getHeight());
            g.setColor(COR_GRADE);
            for(int x = 0; x <= largura; x++) {
                g.drawLine(x * TAMANHO_CELULA, 0, x * TAMANHO_CELULA, imagem.getHeight());
            }
            for(int y = 0; y <= altura; y++) {
                g.drawLine(0, y * TAMANHO_CELULA, imagem.getWidth(), y * TAMANHO_CELULA);
            }
        }

        public void desenhaItem(ItemDesenhavel item, boolean marcaHospital)
        {
            Localizacao local = item.getLocalizacao();
            int px = local.getX() * TAMANHO_CELULA;
            int py = local.getY() * TAMANHO_CELULA;
            g.setColor(item.getCor());
            g.fillRect(px + 1, py + 1, TAMANHO_CELULA - 1, TAMANHO_CELULA - 1);
            if(marcaHospital) {
                g.setColor(Color.WHITE);
                g.drawString("H", px + 4, py + TAMANHO_CELULA - 4);
            }
        }

        protected void paintComponent(Graphics tela)
        {
            super.paintComponent(tela);
            tela.drawImage(imagem, 0, 0, null);
        }
    }
}
