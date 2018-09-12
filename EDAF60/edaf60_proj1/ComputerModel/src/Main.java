
public class Main {
	
	public static void main(String[] args) {
        var lwf = new LongWordFactory();
        var bwf = new ByteWordFactory();
        run("factorial(5) med LongWord", new Factorial("5", lwf), lwf);
        run("factorial(5) med ByteWord", new Factorial("5", bwf), bwf);
        run("factorial(16) med LongWord", new Factorial("16", lwf), lwf);
        run("factorial(16) med ByteWord", new Factorial("16", bwf), bwf);
        run("sum(5) med LongWord", new Sum("5", lwf), lwf);
        run("sum(5) med ByteWord", new Sum("5", bwf), bwf);
        run("sum(16) med LongWord", new Sum("16", lwf), lwf);
        run("sum(16) med ByteWord", new Sum("16", bwf), bwf);
    }

    static void run(String message, Program program, WordFactory wf) {
        System.out.println("================================");
        System.out.println(message);
        System.out.println(program);
        var computer = new Computer(new Memory(1024, wf));
        computer.load(program);
        computer.run();
    }
}

class Factorial extends Program {

    public Factorial  (String value, WordFactory wf) {
        Address n = new Address(0),
                fac = new Address(1);
        add(new Copy(wf.word(value), n));
        add(new Copy(wf.word("1"), fac));
        add(new JumpEq(6, n, wf.word("1")));
        add(new Mul(fac, n, fac));
        add(new Add(n, wf.word("-1"), n));
        add(new Jump(2));
        add(new Print(fac));
        add(new Halt());
    }
}

class Sum extends Program {

    public Sum  (String value, WordFactory wf) {
        Address n = new Address(0),
                sum = new Address(1);
        add(new Copy(wf.word(value), n));
        add(new Copy(wf.word("1"), sum));
        add(new JumpEq(6, n, wf.word("1")));
        add(new Add(sum, n, sum));
        add(new Add(n, wf.word("-1"), n));
        add(new Jump(2));
        add(new Print(sum));
        add(new Halt());
    }

}
