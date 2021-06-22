package br.com.rayanne.rest;

import javax.xml.bind.annotation.*;


@XmlRootElement(name="user") //Essa anotação garante que aquela primeira tag será <user>, sem essa anotação o sistema dá erro porque não sabe o que colocar.
@XmlAccessorType(XmlAccessType.FIELD) // Pega todos os atributos e alguns gets que não estejam cobertos.
public class User {
    @XmlAttribute    //Se eu não informar por essa anotação que o ID é um atributo ele aparecerá como "null" quando rodamos XML.
    private Long id;
    private String name;
    private Integer age;
    private Double salary;

    public User(){
        //Criado porque ao rodar o teste 'deveSalvarUsuarioViaXMLUsandoObjeto' ele reclamou que não tinha um construtor criado sem argumentos.
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
