/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Sekai Kyoretsuna
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.ylf.laye.debug;

import java.io.PrintStream;
import java.util.Objects;

import io.ylf.laye.ast.AST;
import io.ylf.laye.ast.ASTVisitor;
import io.ylf.laye.ast.NodeNullLiteral;
import io.ylf.laye.ast.NodeVariableDef;

/**
 * @author Sekai Kyoretsuna
 */
public class ASTViewer implements ASTVisitor
{
   private PrintStream out;
   
   private int tabs = 0;
   
   public ASTViewer(PrintStream out)
   {
      if (out == null)
      {
         throw new NullPointerException();
      }
      this.out = out;
   }
   
   private String getTabs()
   {
      assert(tabs >= 0);
      if (tabs == 0)
      {
         return("");
      }
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < tabs; i++)
      {
         builder.append("\t");
      }
      return(builder.toString());
   }
   
   private void tprint(String output)
   {
      assert(output != null);
      out.print(getTabs() + output);
   }
   
   private void tprint(Object output)
   {
      out.print(getTabs() + Objects.toString(output));
   }

   private void tprintln(String output)
   {
      assert(output != null);
      out.println(getTabs() + output);
   }

   private void tprint()
   {
      out.print(getTabs());
   }
   
   private void tprintln(Object output)
   {
      out.println(getTabs() + Objects.toString(output));
   }
   
   private void print(String output)
   {
      assert(output != null);
      out.print(output);
   }
   
   private void print(Object output)
   {
      out.print(Objects.toString(output));
   }
   
   private void println(String output)
   {
      assert(output != null);
      out.println(output);
   }
   
   private void println(Object output)
   {
      out.println(Objects.toString(output));
   }
   
   private void println()
   {
      out.println();
   }

   @Override
   public void accept(AST ast)
   {
      ast.forEach(node ->
      {
         tprint();
         node.accept(this);
         println();
      });
   }
   
   @Override
   public void accept(NodeVariableDef node)
   {
      println("VAR {");
      tabs++;
      node.forEach(pair ->
      {
         tprint(pair.a);
         print(" = ");
         pair.b.accept(this);
         println("");
      });
      tabs--;
      tprint("}");
   }
   
   @Override
   public void accept(NodeNullLiteral node)
   {
      print("null");
   }
}
