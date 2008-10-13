package org.jetbrains.idea.maven.dom;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.ConvertContext;
import org.jetbrains.annotations.Nullable;

public class MavenModuleConverter extends MavenReferenceConverter<PsiFile> {
  public String toString(@Nullable PsiFile psiFile, ConvertContext context) {
    VirtualFile file = getFile(context);
    return MavenModuleReference.calcRelativeModulePath(file, psiFile.getVirtualFile());
  }

  protected PsiReference createReference(PsiElement element,
                                         String originalText,
                                         String resolvedText,
                                         TextRange range,
                                         VirtualFile virtualFile,
                                         XmlFile psiFile) {
    return new MavenModuleReference(element, originalText, resolvedText, range, virtualFile, psiFile);
  }
}
