package ch.cern.todo.bll.constants;

public enum EnglishStrings{
    TOO_LONG_TASK_DESCRIPTION("");

    EnglishStrings(String value){
        this.value = value;
    }
    private final String value;
}
