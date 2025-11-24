/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.connectors.doris.sink.writer;

/** Generator label for stream load. */
public class LabelGenerator {
    private final String labelPrefix;
    private final boolean enable2PC;

    public LabelGenerator(String labelPrefix, boolean enable2PC) {
        this.labelPrefix = labelPrefix;
        this.enable2PC = enable2PC;
    }

//    public String generateLabel(long chkId) {
//        return enable2PC
//                ? labelPrefix + "_" + chkId
//                : labelPrefix + "_" + System.currentTimeMillis();
//    }
public String generateLabel(long chkId) {
    return enable2PC
            ? labelPrefix + "_" + chkId
            : getSubStringLabel(labelPrefix) + "_" + System.currentTimeMillis();
}

    public static String getSubStringLabel(String label) {
        if (label.length() > 114) {
            // 找到最后一个下划线的位置，这是前缀部分和标识部分的分隔点
            int lastUnderlineIndex = label.lastIndexOf('_');
            if (lastUnderlineIndex > 0) {
                String lastUnderline = label.substring(0, lastUnderlineIndex);
                int lastSecondUnderlineIndex = lastUnderline.lastIndexOf('_');
                String lastSecondUnderline = lastUnderline.substring(0, lastSecondUnderlineIndex);
                lastSecondUnderline = lastSecondUnderline.substring(0, 90);
                String substring = label.substring(lastSecondUnderlineIndex);
                return lastSecondUnderline + substring;
            }
            // 如果无法正确分割，则直接取前128个字符
            return label.substring(0, 128);
        }
        return label;
    }
}
