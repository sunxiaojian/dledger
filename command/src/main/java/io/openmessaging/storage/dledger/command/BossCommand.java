/*
 * Copyright 2017-2022 The DLedger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openmessaging.storage.dledger.command;

import com.beust.jcommander.JCommander;

import java.util.HashMap;
import java.util.Map;

public class BossCommand {

    public static void main(String[] args) {
        Map<String, BaseCommand> commands = new HashMap<>();
        commands.put("server", new ServerCommand());
        commands.put("append", new AppendCommand());
        commands.put("get", new GetCommand());
        commands.put("readFile", new ReadFileCommand());
        commands.put("leadershipTransfer", new LeadershipTransferCommand());
        JCommander.Builder builder = JCommander.newBuilder();
        for (String cmd : commands.keySet()) {
            builder.addCommand(cmd, commands.get(cmd));
        }
        JCommander jc = builder.build();
        jc.parse(args);

        if (jc.getParsedCommand() == null) {
            jc.usage();
        } else {
            BaseCommand command = commands.get(jc.getParsedCommand());
            if (null != command) {
                command.doCommand();
            } else {
                jc.usage();
            }
        }
    }

}
