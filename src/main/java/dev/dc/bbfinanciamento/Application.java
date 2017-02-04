package dev.dc.bbfinanciamento;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Application {

    private static final String URL = "https://www8.bb.com.br/simulador/formGeral.sml?opcao=detalharLinhaCreditoFinanciamentoCarencia&codigoLinhaCredito=61&_ga=1.255390222.789894944.1455888339";
    private static final String REQUEST_URL = "https://www8.bb.com.br/simulador/formGeral.sml";

    public static void main(String[] args) throws IOException {
        String valorBase = args[0];
        String prazoFinan = args[1];
        
        String prazoCarencia = "0";
        
        if (args.length > 2) {
            prazoCarencia = args[2];
        }
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\Dhiogo\\certificadobb.jks");

        Map<String, String> headers = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        headers.put("Host", "www8.bb.com.br");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "171");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Origin", "https://www8.bb.com.br");
        headers.put("Upgrade-Insecure-Requests", " 1");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Referer", "https://www8.bb.com.br/simulador/formGeral.sml");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4");
        headers.put("Cookie", "nav41493=6b15ed7ffbdb9f93deaf95e0709|2_28; nvgt41493=1485529813954_1_0|0_0|0; _ga=GA1.3.205028103.1479834766; CS___SessionId___0=%7B%22name%22%3A%22SessionId%22%2C%22value%22%3A%2218e9c854-ec07-b9fd-53d5-181f8cbf5730%22%2C%22expires%22%3A%222018-01-31T23%3A48%3A36.520Z%22%2C%22domain%22%3A%22bb.com.br%22%7D; JSESSIONID=ShvyYVpYkpcKbQrPqWY9yyxBvVlbsGdBxJypnQ2vX1wJfwkHv3GW!995470240; nav41493=6b15ed7ffbdb9f93deaf95e0709|2_28; nvgt41493=1485529813954_1_0|0_0|0; _ga=GA1.3.205028103.1479834766; CS___SessionId___0=%7B%22name%22%3A%22SessionId%22%2C%22value%22%3A%2218e9c854-ec07-b9fd-53d5-181f8cbf5730%22%2C%22expires%22%3A%222018-01-31T23%3A48%3A36.520Z%22%2C%22domain%22%3A%22bb.com.br%22%7D; JSESSIONID=JPWnYVzCwrnHyqjCp4mmWDjGbpcp1cgytpnNtYLNFJM0fnJ6HTGh!995470240");

        data.put("opcao", "calcularFinanciamentoCarencia");
        data.put("flagPF", "");
        data.put("codigoLinhaCredito", "61");
        data.put("flagSN", "true");
        data.put("valorBase", valorBase);
        data.put("prazoFinanciamento", prazoFinan);
        data.put("prazoCarencia", "0");
        data.put("fundoAval", "2");
        data.put("formaPgtoFundoAval", "1");

        Document response = Jsoup.connect(REQUEST_URL).headers(headers)
                .data(data).followRedirects(true).referrer("https://www8.bb.com.br/simulador/formGeral.sml")
                .validateTLSCertificates(true)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                .post();
        
        Element tabela = response.getElementById("parcelas");
        
        if (tabela != null) {
            String content = "Parcela,Data do Pagamento,Amortização de Principal, Encargos Básicos Projetados, Pgto de Encargos Básicos, Pgto de Encardos Adicionais, Pgto de CCA, Prestação Total, Saldo Devedor";
            System.out.println(content);
            
            Element tBody = tabela.getElementsByTag("tbody").first();
            
            Elements tRows = tBody.getElementsByTag("tr");
            
            for (Element row : tRows) {
                Elements tValues = row.getElementsByTag("td");
                
                String values = "";
                
                for (Element value : tValues) {
                    if (values.isEmpty()) {
                        values = value.text();
                    } else {
                        values += "," + value.text();
                    }
                }
                
                if (!values.isEmpty()) {
                    content += "\n" + values;
                    System.out.println(values);
                }
            }
        } else {
            System.out.println("Erro ao carregar a página. Verifique os parâmetros passados.");
        }

    }
}
