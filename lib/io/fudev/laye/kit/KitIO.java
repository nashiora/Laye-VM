/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Sekai Kyoretsuna
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.fudev.laye.kit;

import java.nio.file.Paths;

import io.fudev.laye.LayeException;
import io.fudev.laye.kit.io.LayeFile;
import io.fudev.laye.vm.LayeFunction;
import io.fudev.laye.vm.LayeKit;
import io.fudev.laye.vm.LayeObject;
import io.fudev.laye.vm.LayeString;
import io.fudev.laye.vm.LayeVM;

/**
 * @author Sekai Kyoretsuna
 */
public
class KitIO
   extends LayeKit
{
   private static final LayeObject PATHS = new LayeObject();
   
   private static LayeObject PATHS__normalize(LayeVM vm, LayeObject thisObject, LayeObject... args)
   {
      if (args.length == 0)
      {
         throw new LayeException(vm, "expected path to normalize");
      }
      String path = args[0].checkString(vm);
      String normPath = Paths.get(path).normalize().toString();
      return(new LayeString(normPath));
   }
   
   private static LayeObject PATHS__absolute(LayeVM vm, LayeObject thisObject, LayeObject... args)
   {
      if (args.length == 0)
      {
         throw new LayeException(vm, "expected path to make absolute");
      }
      String path = args[0].checkString(vm);
      String absPath = Paths.get(path).toAbsolutePath().normalize().toString();
      return(new LayeString(absPath));
   }
   
   static
   {
      PATHS.setField(null, "normalize", new LayeFunction(KitIO::PATHS__normalize));
      PATHS.setField(null, "absolute", new LayeFunction(KitIO::PATHS__absolute));
   }
   
   public KitIO(LayeVM vm)
   {
      setField(vm, "File", LayeFile.TYPEDEF_FILE);
      setField(vm, "paths", PATHS);
   }
}
