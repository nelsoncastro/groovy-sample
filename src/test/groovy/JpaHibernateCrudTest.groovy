import com.programmerinaction.groovy.domain.entity.Book
import com.programmerinaction.groovy.domain.enums.Operacao
import com.programmerinaction.groovy.domain.util.Executor
import org.junit.Test

import static org.junit.Assert.*

class JpaHibernateCrudTest extends JpaHibernateTest {

    @Test
    void findByIdTest() {
        def book = em.find(Book.class, 1L)
        assertNotNull(book)
    }

    @Test
    void createTest() {
        def book = new Book(id: 5L, title: "AngularJs in Action", description: "AngularJs in Action", isbn: "123456789", versao: 1,
                dataInclusao: new Date(), dataAlteracao: new Date(), codUsuarioAlteracao: "07982246737")

        em.getTransaction().begin()
        em.persist(book)
        em.getTransaction().commit()

        def found = em.find(Book.class, 5L)
        assertNotNull(found)
    }

    @Test
    void findAllTest() {
        def books = em.createQuery("from Book b").getResultList()
        books.each { book -> println "Book ${book.id} \t${book.title}\n" }
        assertNotNull(books)
    }

    @Test
    void listaTest() {
        // overload de operadores
        def lista = []
        println "===> adicionando a e b na lista"
        lista += "a"
        lista += "b"
        println "Itens da lista ap�s adi��o ==> ${lista}"
        println "===> removendo b da lista"
        lista -= "b"
        println "Itens da lista ap�s remo��o ==> ${lista}"
    }

    @Test
    void mapTest() {
        // trabalhando com MAP's
        def map = [:]
        map.nome = "Nelson"
        map.sobrenome = "Castro"

        // Interpola��o de String
        String msg = "key: ${map["nome"]} value: ${map["sobrenome"]}"

        print msg
    }

    @Test
    void stringMultiLineTest() {
        // String multi linhas
        String nome = "Nelson"
        String sobrenome = "Castro"
        String stringMultiLine = """meu nome
                � 
                ${nome}
                ${sobrenome}"""
        print stringMultiLine
    }

    @Test
    void numeroTest() {
        print deltaGroovy(2, 3, 4)
    }

    def deltaGroovy(a, b, c) {
        (b * b) - (4 * a * c)
    }

    /*
    BigDecimal deltaJava(BigDecimal a, BigDecimal b,BigDecimal c) {
        return b.multiply(b).minus(new BigDecimal(4).multiply(a).multiply(c));
    }
    */

    @Test
    void somaTest() {
        println soma("Nelson", 1)
        println soma(1, "Nelson")
    }

    def soma(x, y) {
        x + y
    }

    /* =====> Tipagem Din�mica <===== */

    class Homem {
        def fale(valor) {
            println "${getClass()} dizendo ${valor}"
        }
    }

    class Mulher {
        def fale(valor) {
            println "${getClass()} dizendo ${valor}"
        }
    }

    void diga(objeto, valor) {
        objeto.fale(valor)
    }

    @Test
    void faleTest() {
        diga(new Homem(), "oi")
        diga(new Mulher(), "oi")
    }

    /* =====> Clousure � um tipo que armazena c�digo execut�vel <===== */

    @Test
    void clousureTest() {

        // "it" is implicit parameter (default parameter)
        def clousure = { println "ol� ${it}" }
        clousure("Nelson")

        def clousureTwo = { nome -> println "ol� ${nome}" }
        clousureTwo("Nelson")

        def clousureThree = { nome, cidade -> println "ol� ${nome} de ${cidade}" }
        clousureThree("Nelson", "Bras�lia")
    }

    class Pessoa {
        String nome

        // Clousure
        def saudacao = {
            println "Ol� ${nome}! ${it}"
        }
    }

    void applyClousure(lista, clousure) {
        for (item in lista)
            clousure(item)
    }

    @Test
    void clousureInClassTest() {
        def pessoa = new Pessoa(nome: "Nelson")

        def lista = ["Bom dia", "Boa tarde", "Boa noite"]

        lista.each { pessoa.saudacao }
        // or
        applyClousure(lista, pessoa.saudacao)
        // or
        lista.each { println "Clousure An�nima -> Nelson ${it}" }

        def carro = new Carro(velocidadeMaxima: 300)
        pessoa.saudacao = carro.vrum
        pessoa.saudacao()
    }

    class Carro {
        int velocidadeMaxima

        // Clousure
        def vrum = {
            print "Sou r�pido! velocidade ${velocidadeMaxima} km/h"
        }
    }

    /* =====> Reflex�o (invoca��o din�mica) <===== */

    class PessoaFisica {
        String nome
        String sobreNome
        int idade

        PessoaFisica() {
            println "Construtor default de PessoaFisica foi chamado!!!"
        }

        void digaAlgo() {
            println "oi, eu sou ${nome} ${sobreNome} e tenho ${idade} anos"
        }

        void digaNaoAlgo() {
            println "N�o algo"
        }

        String toString() {
            "${nome} ${sobreNome} - ${idade}"
        }

        boolean equals(Object obj) {
            // == em groovy equivale ao equals em java (overload de operadores)
            (obj.nome == this.nome) && (obj.sobreNome == this.sobreNome)
        }
    }

    class Alone {

        Alone() {
            println "Construtor default de Alone foi chamado!!!"
        }

        void metodoAlone() {
            println "forever alone"
        }
    }

    @Test
    void mixinTest() {
        PessoaFisica.mixin Alone
        def pessoa = new PessoaFisica()
        pessoa.metodoAlone()
    }

    @Test
    void reflexaoTest() {
        println "===> properties"
        PessoaFisica.metaClass.properties.each { println it.name }

        println "===> methods"
        PessoaFisica.metaClass.metaMethods.each { println it.name }

        // Adicionando comportamento em tempo de execu��o MUTABILIDADE!!!
        PessoaFisica.metaClass.novoMetodo = { println "new method fired!!!" }

        def pessoa = new PessoaFisica(nome: "Nelson", idade: 40)

        // Metaprograma��o
        def lista = ["digaAlgo", "digaNaoAlgo"]
        for (item in lista)
            pessoa."${item}"()

        def fields = ["nome", "idade"]
        for (field in fields)
            println pessoa."${field}"

        pessoa.novoMetodo()
    }

    @Test
    void reflexao2Test() {
        // Alterando comportamento de um m�todo em tempo de execu��o
        PessoaFisica.metaClass.digaAlgo = { println "novo comportamento para diga algo!!!" }
        def pessoa = new PessoaFisica(nome: "Nelson", idade: 40)
        pessoa.digaAlgo()
    }

    @Test
    void reflexao3Test() {
        // Intercepetando chamada de m�todos
        PessoaFisica.metaClass.invokeMethod = { String nome, parametros ->
            println "Vou chamar ${nome}"

            def metodo = PessoaFisica.metaClass.getMetaMethod(nome)

            // toda clousure tem um delegate (instancia que est� executando a clousure)
            def retorno = metodo.invoke(delegate, parametros)

            println "Chamei ${metodo}"
            retorno
        }
        def pessoa = new PessoaFisica(nome: "Nelson", idade: 40)
        pessoa.digaAlgo()
    }

    /* =====> Collections <===== */

    @Test
    void collectionsTest() {
        /* criando lista */
        def pessoas = [new PessoaFisica(nome: "Jo�o", sobreNome: "Batista", idade: 40),
                       new PessoaFisica(nome: "Jeremias", sobreNome: "Lima", idade: 35)]

        println "=====> Adicionando Marcos Alexandre na lista... <====="
        println pessoas
        pessoas += new PessoaFisica(nome: "Marcos", sobreNome: "Alexandre", idade: 25)
        println "=====> Conte�do da lista ap�s adi��o... <====="
        println pessoas

        /* criando nova lista com itens da lista anterior + novos itens */
        def novaLista = pessoas + new PessoaFisica(nome: "Paulo", sobreNome: "Tarso", idade: 45)
        println "=====> Nova Lista <====="
        println novaLista

        /* removendo itens da lista */
        println "=====> Removendo Jo�o Batista da lista... <====="
        println novaLista
        novaLista -= new PessoaFisica(nome: "Jo�o", sobreNome: "Batista")
        println "=====> Conte�do da lista ap�s remo��o... <====="
        println novaLista
    }

    @Test
    void collectionsSearchTest() {
        /* criando lista */
        def pessoas = [new PessoaFisica(nome: "Jo�o", sobreNome: "Lobo", idade: 40),
                       new PessoaFisica(nome: "Jeremias", sobreNome: "Lima", idade: 35),
                       new PessoaFisica(nome: "Jo�o", sobreNome: "Alexandre", idade: 60)]

        println "=====> Imprimindo nome da pessoas utilizando clousure o m�todo each da Lista...."
        pessoas.each { pessoa -> println "${pessoa.nome} ${pessoa.sobreNome}" }

        println "\n=====> Pesquisando na lista com o m�todo find...."
        println pessoas.find { it.nome == "Jo�o" && it.sobreNome == "Lobo" }

        println "\n=====> Pesquisando na lista com o m�todo findAll...."
        println pessoas.findAll { it.nome == "Jo�o" }
    }

    /* =====> Maps <===== */

    @Test
    void mapsTest() {
        def mapOne = [:]
        println mapOne.getClass()

        def mapTwo = [numero: 2, pessoa: new PessoaFisica(nome: "Jo�o", sobreNome: "Batista", idade: 40)]

        // adicionando elementos ao MAP
        mapTwo.inteiro = 4 // or map["inteiro"]=4
        println mapTwo
    }

    @Test
    void maps2Test() {
        def pessoaNaoReal = [nome: "Jo�o", sobreNome: "Carlos"]
        println pessoaNaoReal.getClass()
        def pessoaReal = [nome: "Jo�o", sobreNome: "Carlos"]
        println pessoaReal.getClass()

        println "Pessoa real -> ${pessoaReal.sobreNome}"
        println "Pessoa n�o real -> ${pessoaNaoReal.sobreNome}"

        // adicionando comportamento ao MAP
        pessoaNaoReal.digaOi = { println "oi" }
        pessoaNaoReal.digaOi()
    }

    @Test
    void maps3Test() {
        def pessoaNaoReal = [nome: "Jo�o", sobreNome: "Carlos"]
        pessoaNaoReal.remove("nome")
        println pessoaNaoReal
        assertNull(pessoaNaoReal.nome)
    }

    @Test
    void basicClousureTest() {
        def lista = [1, 2, 3, 4, 6, 7, 8, 9, 10]
        def founds = lista.findAll { it > 4 }
        println founds
        assertEquals(5, founds.size())
    }

    @Test
    void groovyEmbeddedTest() {
        def x = 2
        def y = 5
        def resultado = new Executor().execute(x, y, Operacao.SOMA)
        assertEquals(7.0, resultado)
    }
}