/*-
 * #%L
 * Elastic APM Java agent
 * %%
 * Copyright (C) 2018 Elastic and contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package co.elastic.apm.benchmark.reporter;

import co.elastic.apm.impl.payload.Payload;
import co.elastic.apm.report.PayloadSender;
import org.openjdk.jmh.runner.RunnerException;

/**
 * Measures how many events the reporter can handle,
 * not accounting for JSON serialization and sending payloads over HTTP.
 */
public class NoopReporterBenchmark extends AbstractReporterBenchmark {

    /**
     * Convenience benchmark run method
     * <p>
     * For more accurate results, execute {@code mvn clean package} and run the benchmark via
     * {@code java -jar apm-agent-benchmarks/target/benchmarks.jar -prof gc}
     */
    public static void main(String[] args) throws RunnerException {
        run(NoopReporterBenchmark.class);
    }

    protected PayloadSender getPayloadSender() {
        return new PayloadSender() {
            @Override
            public void sendPayload(Payload payload) {
                payload.recycle();
            }

            @Override
            public long getReported() {
                return 0;
            }

            @Override
            public long getDropped() {
                return 0;
            }
        };
    }
}
