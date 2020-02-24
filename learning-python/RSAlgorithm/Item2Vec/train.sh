word2vec -train train.data -output item_vector.data -size 128 -window 5 -sample 1e-3 -negative 5 -hs 0 -binary 0 -cbow 0 -iter 100
