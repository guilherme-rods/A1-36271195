package br.edu.up.DAO;

import br.edu.up.Modelos.Aluno;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao {
    private String arquivo;
    private String header = "matricula;nome;nota";

    public AlunoDao(String arquivo) {
        this.arquivo = "./dbbanco/" + arquivo;
    }

    public List<Aluno> lerAlunos() throws IOException {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().equalsIgnoreCase(header))
                    continue;

                String[] dados = linha.split(";");
                String matricula = dados[0];
                String nome = dados[1];
                double nota = Double.parseDouble(dados[2].replace(",", "."));

                Aluno aluno = new Aluno();
                aluno.setMatricula(matricula);
                aluno.setNome(nome);
                aluno.setNota(nota);
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    public void gravarAlunos(List<Aluno> alunos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            bw.write(header + "\n");
            for (Aluno aluno : alunos) {
                bw.write(toStringCsv(aluno) + "\n");
            }
        }
    }

    private String toStringCsv(Aluno aluno) {
        return aluno.getMatricula() + ";" + aluno.getNome() + ";" + aluno.getNota();
    }
}