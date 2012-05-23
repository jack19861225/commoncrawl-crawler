/**
 * Copyright 2012 - CommonCrawl Foundation
 * 
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package org.commoncrawl.mapred.pipelineV3.crawllistgen;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.commoncrawl.crawl.common.internal.CrawlEnvironment;
import org.commoncrawl.mapred.pipelineV3.CrawlPipelineStep;
import org.commoncrawl.mapred.pipelineV3.CrawlPipelineTask;
import org.commoncrawl.mapred.segmenter.Segmenter;
import org.commoncrawl.util.JobBuilder;

/**
 * 
 * @author rana
 *
 */
public class GenSegmentsStep extends CrawlPipelineStep {

  public static final String OUTPUT_DIR_NAME = "segments";

  private static final Log LOG = LogFactory.getLog(GenSegmentsStep.class);

  public GenSegmentsStep(CrawlPipelineTask task) {
    super(task, "Generate Segments", OUTPUT_DIR_NAME);
  }

  @Override
  public Log getLogger() {
    return LOG;
  }

  @Override
  public void runStep(Path outputPathLocation) throws IOException {

    Path tempPath = JobBuilder.tempDir(getConf(), OUTPUT_DIR_NAME);

    Path bundlePath = getOutputDirForStep(GenBundlesStep.class);

    Segmenter.generateCrawlSegments(getTaskIdentityId(), CrawlEnvironment.CRAWLERS, bundlePath, tempPath);

    getFileSystem().rename(tempPath, outputPathLocation);

  }

}
