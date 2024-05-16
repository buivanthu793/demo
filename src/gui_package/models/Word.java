package gui_package.models;

import java.util.ArrayList;
import java.util.Scanner;

public class Word {
    private int id;
    private String word_target;
    private String word_type;
    private String word_explain;
    private String pronunciation;
    private String example;

    // hàm khởi tạo từ 5 thành phần
    public Word(String word_target_, String word_type_, String word_explain_, String pronunciation_, String example_) {
        word_target = word_target_;
        word_type = word_type_;
        word_explain = word_explain_;
        pronunciation = pronunciation_;
        example = example_;
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_type() {
        return word_type;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getExample() {
        return example;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_type(String word_type) {
        this.word_type = word_type;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setExample(String example) {
        this.example = example;
    }
}