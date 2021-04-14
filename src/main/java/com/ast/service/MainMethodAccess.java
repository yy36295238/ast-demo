package com.ast.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author yangyu
 * @date 2021/4/6 16:20
 */
public class MainMethodAccess {
    public static void main(String[] args) throws FileNotFoundException {

        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream("D:\\workspace\\java\\my\\ast-demo\\src\\main\\java\\com\\ast\\service\\HelloService.java");

        CompilationUnit cu = StaticJavaParser.parse(in);

        // visit and print the methods names
        new MethodVisitor().visit(cu, null);
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter {
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this
            // CompilationUnit, including inner class methods
            System.out.println("method: " + n.getName());
            n.getComment().ifPresent(comment -> System.out.println("desc: \n\r" + comment.toString()));
            System.out.println("parameters: " + n.getParameters());

            // 获取方法体内的属性
            n.getBody().ifPresent(c -> {
                System.err.println(n.getName());
                for (Node childNode : c.getChildNodes()) {
                    System.err.printf("line: %s%n", childNode);
                }

                for (Node node : c.getChildNodes()) {
                    Node node1 = node.getChildNodes().get(0).getChildNodes().get(0);
                    if (node1 instanceof VariableDeclarator) {
                        Expression expression = ((VariableDeclarator) node1).getInitializer().get();
                        if (expression instanceof MethodCallExpr) {
                            Node node2 = ((MethodCallExpr) expression).getChildNodes().get(0);
                            System.out.println("serviceVar: " + node2);
                        }
                    }

                }
            });
        }

        @Override
        public void visit(FieldDeclaration n, Object arg) {
            System.out.println("class var: " + n.getVariables().get(0));
        }

        @Override
        public void visit(BlockStmt n, Object arg) {
            n.getStatements().forEach(c -> {
                System.out.println("block: " + c);
            });
        }

        @Override
        public void visit(VariableDeclarator n, Object arg) {
            System.out.println("VariableDeclarator: " + n.getNameAsString());
        }
    }
}
