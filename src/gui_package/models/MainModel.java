package gui_package.models;

import gui_package.controllers.DictionaryController;

import java.sql.*;

public class MainModel {
    static String jdbcUrl = "jdbc:sqlite:src/gui_package/models/database.db"; //jdbc:sqlite:dictionary_gui\src\gui_package\models\database.db
    static Connection connection;
    static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createWord(Word word) throws SQLException {
        String sql = "INSERT INTO words (Word, Type, Meaning, Pronunciation, Example, Synonym, Antonyms) " +
                     "VALUES ('" + DictionaryController.sanitizeInput(word.getWord_target()) +
                     "', '" + DictionaryController.sanitizeInput(word.getWord_type()) + "', '" +
                     DictionaryController.sanitizeInput(word.getWord_explain()) + "', '" +
                     DictionaryController.sanitizeInput(word.getPronunciation()) + "', '" +
                     DictionaryController.sanitizeInput(word.getExample()) + "', '', '')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public static Word getWord(int id) throws SQLException {
        String sql = "SELECT * FROM words WHERE ID=" + id;
        ResultSet result = statement.executeQuery(sql);
        int word_id = result.getInt("Id");
        String word_target = result.getString("Word");
        String word_type = result.getString("Type");
        String word_explain = result.getString("Meaning");
        String word_pronunciation = result.getString("Pronunciation");
        String word_example = result.getString("Example");

        Word returnWord = new Word(word_target, word_type, word_explain, word_pronunciation, word_example);
        returnWord.setId(word_id);
        return returnWord;
    }

    public static Word getWord(String matchWord) throws SQLException {
        String sql = "SELECT * FROM words WHERE Word LIKE '" + matchWord + "'";
        ResultSet result = statement.executeQuery(sql);
        int word_id = result.getInt("Id");
        String word_target = DictionaryController.sanitizeInput(result.getString("Word"));
        String word_type = DictionaryController.sanitizeInput(result.getString("Type"));
        String word_explain = DictionaryController.sanitizeInput(result.getString("Meaning"));
        String word_pronunciation = DictionaryController.sanitizeInput(result.getString("Pronunciation"));
        String word_example = DictionaryController.sanitizeInput(result.getString("Example"));

        Word returnWord = new Word(word_target, word_type, word_explain, word_pronunciation, word_example);
        returnWord.setId(word_id);
        return returnWord;
    }

    public static boolean findWord(String matchWord) throws SQLException {
        String sql = "SELECT * FROM words WHERE Word LIKE '" + matchWord + "'";
        ResultSet result = statement.executeQuery(sql);
        return result.getString("Word") == null;
    }

    public static void updateWord(int id, String wordType, String wordMeaning,
                                  String wordPro, String wordEx) throws SQLException {
        String sql = "UPDATE words SET" +
                     " Type = '" + DictionaryController.sanitizeInput(wordType) + "'," +
                     " Meaning = '" + DictionaryController.sanitizeInput(wordMeaning) + "'," +
                     " Pronunciation = '" + DictionaryController.sanitizeInput(wordPro) + "'," +
                     " Example = '" + DictionaryController.sanitizeInput(wordEx) + "'," +
                     " Synonym = ''," +
                     " Antonyms = ''" +
                     "WHERE Id = " + id + ";";
        System.out.println(sql);
        statement.executeUpdate(sql);

    }

    public static void removeWord(int id) throws SQLException {
        String sql = "DELETE FROM words WHERE Id=" + id + ";";
        statement.executeUpdate(sql);
    }

    public static void removeWord(String matchWord) throws SQLException {
        String sql = "DELETE FROM words WHERE Word LIKE " + "'" + matchWord + "'" + ";";
        statement.executeUpdate(sql);
    }

    public static ResultSet getWordByString(String wordString) throws SQLException {
        String sql = "SELECT Word FROM words WHERE Word LIKE '%" + wordString + "%' ORDER BY length(Word) ASC;";
        return statement.executeQuery(sql);
    }

    public static String getWordleWord() throws SQLException {
        String sql = "SELECT * FROM wordle_wordList ORDER BY RANDOM() LIMIT 1;";
        return statement.executeQuery(sql).getString("Word");
    }

    public static String verifyWordleWord(String word) throws SQLException {
        String currWord = statement.executeQuery("SELECT * FROM wordle_wordList WHERE Word LIKE '" + word + "'").getString("Word");
        if (currWord == null) {
            currWord = statement.executeQuery("SELECT * FROM wordle_wildcard WHERE Word LIKE '" + word + "'").getString("Word");
        }
        return currWord;
    }

    public static void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }

//    public static void main(String[] args) throws SQLException, UnsupportedEncodingException {
//        ResultSet a = getWordByString("house");
//        while (a.next()) {
//            System.out.println(a.getString(2));
//        }
//        System.out.println(getWordleWord());
//    }
}