public class Compilation {
  public static void main(String[] args) {
    Test t = new Test();
    String x = t.foo;
    int y = t.bar;
  }
  
}
class Test{
  private Test(){}
  public String foo;
  private int bar;
}