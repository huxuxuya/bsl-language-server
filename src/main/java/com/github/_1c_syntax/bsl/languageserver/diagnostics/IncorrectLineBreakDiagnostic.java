/*
 * This file is a part of BSL Language Server.
 *
 * Copyright © 2018-2021
 * Alexey Sosnoviy <labotamy@gmail.com>, Nikita Gryzlov <nixel2007@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * BSL Language Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * BSL Language Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BSL Language Server.
 */
package com.github._1c_syntax.bsl.languageserver.diagnostics;

import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticInfo;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticMetadata;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticSeverity;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticTag;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticType;
import com.github._1c_syntax.bsl.languageserver.utils.Ranges;
import com.github._1c_syntax.bsl.languageserver.utils.Trees;
import com.github._1c_syntax.bsl.parser.BSLParser;
import com.github._1c_syntax.bsl.parser.BSLParserRuleContext;
import com.github._1c_syntax.utils.CaseInsensitivePattern;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DiagnosticMetadata(
  type = DiagnosticType.CODE_SMELL,
  severity = DiagnosticSeverity.INFO,
  minutesToFix = 2,
  tags = {
    DiagnosticTag.STANDARD,
    DiagnosticTag.BADPRACTICE
  }

)
public class IncorrectLineBreakDiagnostic extends AbstractDiagnostic {

  private static final Pattern INCORRECT_START_LINE_PATTERN = CaseInsensitivePattern.compile(
    "(:?^\\s*)(:?\\)|;|,|\\);)"
  );

  private static final Pattern INCORRECT_END_LINE_PATTERN = CaseInsensitivePattern.compile(
    "(\\s+(?:ИЛИ|И|OR|AND)|\\+|-|\\/|%|\\*)\\s*(?:\\/\\/.*)?$"
  );


  @Override
  protected void check() {

    String[] ContentList;
    Object Range;

    ContentList = documentContext.getContentList();

    checkContent(ContentList, INCORRECT_START_LINE_PATTERN);
    checkContent(ContentList, INCORRECT_END_LINE_PATTERN);

  }

  private void checkContent(String[] ContentList, Pattern Pattern) {

    String CheckText;
    boolean startError;

    for (int i = 0; i < ContentList.length; i++) {

      CheckText = ContentList[i];

      Matcher matcher = Pattern.matcher(CheckText);
      startError = matcher.find();
      if (startError) {
        diagnosticStorage.addDiagnostic(i+1,1,i+1,0);
      }
    }
  }
}