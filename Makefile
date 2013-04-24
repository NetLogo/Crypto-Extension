ifeq ($(origin NETLOGO), undefined)
  NETLOGO=../..
endif

ifneq (,$(findstring CYGWIN,$(shell uname -s)))
  COLON=\;
else
  COLON=:
endif

SRCS=$(shell find src -type f -name '*.scala')
EXT_NAME=crypto

COMMONS_CODEC_NAME=commons-codec-1.7
COMMONS_CODEC_JAR=$(COMMONS_CODEC_NAME).jar
COMMONS_CODEC_PACK=$(COMMONS_CODEC_JAR).pack.gz

JAR_REPO=http://ccl.northwestern.edu/devel/

$(EXT_NAME).jar $(EXT_NAME).jar.pack.gz: $(SRCS) $(COMMONS_CODEC_JAR) manifest.txt Makefile
	mkdir -p classes
	$(SCALA_HOME)/bin/scalac -deprecation -unchecked -encoding us-ascii -classpath $(COMMONS_CODEC_JAR)$(COLON)$(NETLOGO)/NetLogo.jar -d classes $(SRCS)
	jar cmf manifest.txt $(EXT_NAME).jar -C classes .
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip $(EXT_NAME).jar.pack.gz $(EXT_NAME).jar

$(COMMONS_CODEC_JAR) $(COMMONS_CODEC_PACK):
	curl -f -s -S $(JAR_REPO)$(COMMONS_CODEC_JAR) -o $(COMMONS_CODEC_JAR)
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip $(COMMONS_CODEC_PACK) $(COMMONS_CODEC_JAR)

$(EXT_NAME).zip: $(EXT_NAME).jar
	rm -rf $(EXT_NAME)
	mkdir $(EXT_NAME)
	cp -rp $(EXT_NAME).jar $(EXT_NAME).jar.pack.gz README.md Makefile src manifest.txt $(EXT_NAME)
	zip -rv $(EXT_NAME).zip $(EXT_NAME)
	rm -rf $(EXT_NAME)
