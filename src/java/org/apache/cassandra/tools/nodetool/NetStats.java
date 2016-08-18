/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.tools.nodetool;

import io.airlift.command.Command;
import io.airlift.command.Option;
import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool.NodeToolCmd;
import org.apache.cassandra.tools.nodetool.stats.NetStatsHolder;
import org.apache.cassandra.tools.nodetool.stats.NetStatsPrinter;
import org.apache.cassandra.tools.nodetool.stats.*;

@Command(name = "netstats", description = "Print network information on provided host (connecting node by default)")
public class NetStats extends NodeToolCmd
{
    @Option(title = "human_readable",
            name = {"-H", "--human-readable"},
            description = "Display bytes in human readable form, i.e. KiB, MiB, GiB, TiB")
    private boolean humanReadable = false;

    @Option(title = "format",
            name = {"-F", "--format"},
            description = "Output format (json, yaml)")
    private String outputFormat = "";


    @Override
    public void execute(NodeProbe probe)
    {
        if (!outputFormat.isEmpty() && !"json".equals(outputFormat) && !"yaml".equals(outputFormat))
            throw new IllegalArgumentException("arguments for -F are json,yaml only.");

        StatsHolder data = new NetStatsHolder(probe, humanReadable);
        StatsPrinter printer = NetStatsPrinter.from(outputFormat);
        printer.print(data, System.out);
    }
}