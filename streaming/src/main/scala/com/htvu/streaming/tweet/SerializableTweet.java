package com.htvu.streaming.tweet;


import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * For now, Spark does not support Avro. This class is just a quick
 * workaround that (de)serializes Tweet objects using Avro.
 */

