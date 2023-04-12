package uc.eecs.indexing;

import org.eclipse.jdt.core.dom.*;
import uc.eecs.indexing.buglocator.ASTCreator;
import uc.eecs.indexing.buglocator.Splitter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {

  private CompilationUnit cu = null;

  public FileParser(File file) {
    ASTCreator creator = new ASTCreator();
    creator.getFileContent(file);
    cu = creator.getCompilationUnit();
  }

  public String getPackageName() {

    return cu.getPackage() == null ? "" : cu.getPackage().getName().getFullyQualifiedName();
  }

  public String[] getContent() {
    String[] tokensInSourceCode = Splitter.splitSourceCode(this.deleteNoNeededNode());
    StringBuffer sourceCodeContentBuffer = new StringBuffer();
    for (String token : tokensInSourceCode) {
      sourceCodeContentBuffer.append(token + " ");
    }
    String content = sourceCodeContentBuffer.toString().toLowerCase();
    return content.split(" ");
  }

  private String deleteNoNeededNode() {
    cu.accept(
        new ASTVisitor() {
          public boolean visit(AnnotationTypeDeclaration node) {
            if (node.isPackageMemberTypeDeclaration()) {

              node.delete();
            }
            return super.visit(node);
          }
        });
    cu.accept(
        new ASTVisitor() {
          public boolean visit(PackageDeclaration node) {
            node.delete();
            return super.visit(node);
          }
        });
    cu.accept(
        new ASTVisitor() {
          public boolean visit(ImportDeclaration node) {
            node.delete();
            return super.visit(node);
          }
        });
    return cu.toString();
  }

  public String[] getClassNameAndMethodName() {
    String content = (this.getAllClassName() + " " + this.getAllMethodName()).toLowerCase();
    return content.split(" ");
  }

  private String getAllClassName() {
    ArrayList<String> classNameList = new ArrayList<String>();
    for (int i = 0; i < cu.types().size(); i++) {
      TypeDeclaration type = (TypeDeclaration) cu.types().get(i);
      String name = type.getName().getFullyQualifiedName();
      classNameList.add(name);
    }
    String allClassName = "";
    for (String className : classNameList) {
      allClassName += className + " ";
    }
    return allClassName.trim();
  }

  private String getAllMethodName() {
    ArrayList<String> methodNameList = new ArrayList<String>();
    for (int i = 0; i < cu.types().size(); i++) {
      TypeDeclaration type = (TypeDeclaration) cu.types().get(i);
      MethodDeclaration[] methodDecls = type.getMethods();
      for (MethodDeclaration methodDecl : methodDecls) {
        String methodName = methodDecl.getName().getFullyQualifiedName();
        methodNameList.add(methodName);
      }
    }
    String allMethodName = "";
    for (String methodName : methodNameList) {
      allMethodName += methodName + " ";
    }
    return allMethodName.trim();
  }

  public void getImport(final FileWriter writeImport) {
    cu.accept(
        new ASTVisitor() {
          @Override
          public boolean visit(ImportDeclaration node) {
            try {
              writeImport.write(node.getName() + " ");
            } catch (IOException e) {
              e.printStackTrace();
            }
            return super.visit(node);
          }
        });
  }
}
