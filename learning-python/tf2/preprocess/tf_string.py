import tensorflow as tf

print(tf.constant(u"Thanks 😊"))
print(tf.constant([u"You're", u"welcome!"]).shape)

# Unicode string, represented as a UTF-8 encoded string scalar.
text_utf8 = tf.constant(u"语言处理")
print(text_utf8)

# Unicode string, represented as a UTF-16-BE encoded string scalar.
text_utf16be = tf.constant(u"语言处理".encode("UTF-16-BE"))
print(text_utf16be)

# Unicode string, represented as a vector of Unicode code points.
text_chars = tf.constant([ord(char) for char in u"语言处理"])
print(text_chars)

print(tf.strings.unicode_decode(text_utf8,
                          input_encoding='UTF-8'))

print(tf.strings.unicode_encode(text_chars,
                          output_encoding='UTF-8'))

print(tf.strings.unicode_transcode(text_utf8,
                             input_encoding='UTF8',
                             output_encoding='UTF-16-BE'))

# A batch of Unicode strings, each represented as a UTF8-encoded string.
batch_utf8 = [s.encode('UTF-8') for s in
              [u'hÃllo',  u'What is the weather tomorrow',  u'Göödnight', u'😊']]
batch_chars_ragged = tf.strings.unicode_decode(batch_utf8,
                                               input_encoding='UTF-8')
for sentence_chars in batch_chars_ragged.to_list():
    print(sentence_chars)

batch_chars_padded = batch_chars_ragged.to_tensor(default_value=-1)
print(batch_chars_padded.numpy())

batch_chars_sparse = batch_chars_ragged.to_sparse()

print(tf.strings.unicode_encode([[99, 97, 116], [100, 111, 103], [ 99, 111, 119]],
                          output_encoding='UTF-8'))

print(tf.strings.unicode_encode(batch_chars_ragged, output_encoding='UTF-8'))

print(tf.strings.unicode_encode(
    tf.RaggedTensor.from_sparse(batch_chars_sparse),
    output_encoding='UTF-8'))

print(tf.strings.unicode_encode(
    tf.RaggedTensor.from_tensor(batch_chars_padded, padding=-1),
    output_encoding='UTF-8')
)

# Note that the final character takes up 4 bytes in UTF8.
thanks = u'Thanks 😊'.encode('UTF-8')
num_bytes = tf.strings.length(thanks).numpy()
num_chars = tf.strings.length(thanks, unit='UTF8_CHAR').numpy()
print('{} bytes; {} UTF-8 characters'.format(num_bytes, num_chars))

# default: unit='BYTE'. With len=1, we return a single byte.
print(tf.strings.substr(thanks, pos=7, len=1).numpy())

# Specifying unit='UTF8_CHAR', we return a single character, which in this case
# is 4 bytes.
print(tf.strings.substr(thanks, pos=7, len=1, unit='UTF8_CHAR').numpy())

print(tf.strings.unicode_split(thanks, 'UTF-8').numpy())

codepoints, offsets = tf.strings.unicode_decode_with_offsets(u"🎈🎉🎊", 'UTF-8')

for (codepoint, offset) in zip(codepoints.numpy(), offsets.numpy()):
    print("At byte offset {}: codepoint {}".format(offset, codepoint))

uscript = tf.strings.unicode_script([33464, 1041])  # ['芸', 'Б']

print(uscript.numpy())  # [17, 8] == [USCRIPT_HAN, USCRIPT_CYRILLIC]

print(tf.strings.unicode_script(batch_chars_ragged))

# dtype: string; shape: [num_sentences]
#
# The sentences to process.  Edit this line to try out different inputs!
sentence_texts = [u'Hello, world.', u'世界こんにちは']

# dtype: int32; shape: [num_sentences, (num_chars_per_sentence)]
#
# sentence_char_codepoint[i, j] is the codepoint for the j'th character in
# the i'th sentence.
sentence_char_codepoint = tf.strings.unicode_decode(sentence_texts, 'UTF-8')
print(sentence_char_codepoint)

# dtype: int32; shape: [num_sentences, (num_chars_per_sentence)]
#
# sentence_char_scripts[i, j] is the unicode script of the j'th character in
# the i'th sentence.
sentence_char_script = tf.strings.unicode_script(sentence_char_codepoint)
print(sentence_char_script)

# dtype: bool; shape: [num_sentences, (num_chars_per_sentence)]
#
# sentence_char_starts_word[i, j] is True if the j'th character in the i'th
# sentence is the start of a word.
sentence_char_starts_word = tf.concat(
    [tf.fill([sentence_char_script.nrows(), 1], True),
     tf.not_equal(sentence_char_script[:, 1:], sentence_char_script[:, :-1])],
    axis=1)

# dtype: int64; shape: [num_words]
#
# word_starts[i] is the index of the character that starts the i'th word (in
# the flattened list of characters from all sentences).
word_starts = tf.squeeze(tf.where(sentence_char_starts_word.values), axis=1)
print(word_starts)

# dtype: int32; shape: [num_words, (num_chars_per_word)]
#
# word_char_codepoint[i, j] is the codepoint for the j'th character in the
# i'th word.
word_char_codepoint = tf.RaggedTensor.from_row_starts(
    values=sentence_char_codepoint.values,
    row_starts=word_starts)
print(word_char_codepoint)

# dtype: int64; shape: [num_sentences]
#
# sentence_num_words[i] is the number of words in the i'th sentence.
sentence_num_words = tf.reduce_sum(
    tf.cast(sentence_char_starts_word, tf.int64),
    axis=1)

# dtype: int32; shape: [num_sentences, (num_words_per_sentence), (num_chars_per_word)]
#
# sentence_word_char_codepoint[i, j, k] is the codepoint for the k'th character
# in the j'th word in the i'th sentence.
sentence_word_char_codepoint = tf.RaggedTensor.from_row_lengths(
    values=word_char_codepoint,
    row_lengths=sentence_num_words)
print(sentence_word_char_codepoint)

tf.strings.unicode_encode(sentence_word_char_codepoint, 'UTF-8').to_list()
