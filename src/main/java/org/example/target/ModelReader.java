package org.example.target;

import org.example.target.models.TargetModelRecord;

import java.io.IOException;
import java.util.Set;

public interface ModelReader {

    Set<TargetModelRecord> read() throws IOException;
}
