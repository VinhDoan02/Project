
/*
*I included a bruteforce method at the end that actually would not work well with large dataset.
*/

    String swapLexOrder(String str, int[][] pairs) {

        ArrayList<TreeSet<Integer>> joinPairList = joinPair(pairs);

        HashMap<TreeSet<Integer>,ArrayList<Character>> charMapping = retrieveChar(str,joinPairList);

        String result = swapStringOrder(str,charMapping);

        return result;
    }

    /**
     *This method would take the double array of indices and combine pairs to form a larger set when having overlapping indices.
     */
    public ArrayList<TreeSet<Integer>> joinPair(int[][] pairs)
    {
        ArrayList<TreeSet<Integer>> jointPairList = new ArrayList<TreeSet<Integer>>();

        int size = pairs.length;
        for(int i = 0 ; i < size ; i++)
        {
            int first = pairs[i][0];
            int second = pairs[i][1];

            int jointListSize = jointPairList.size();
            boolean findMatch = false;
            ArrayList<Integer> matchingSet = new ArrayList<>();
            for(int j = 0 ; j < jointListSize; j++)
            {
                TreeSet<Integer> tempSet = jointPairList.get(j);

                if(tempSet.contains(first) || tempSet.contains(second))
                {
                    findMatch = true;
                    matchingSet.add(j);
                    tempSet.add(first);
                    tempSet.add(second);
                }
            }

            if(!findMatch)
            {
                TreeSet<Integer> tempSet = new TreeSet<>();
                tempSet.add(first);
                tempSet.add(second);
                jointPairList.add(tempSet);
            }else
            {
				/*
				*this part will combine two larger sets together
				*in case that the current pair indices would overlap with both sets
                *this can be at most two larger set that needs to be combine
				*because each original indices are a pairs.
				*/
                if(matchingSet.size() >1)
                {
                    int indice1 = matchingSet.get(0);
                    int indice2 = matchingSet.get(1);

                    TreeSet<Integer> tempSet1 = jointPairList.get(indice1);
                    TreeSet<Integer> tempSet2 = jointPairList.get(indice2);

                    jointPairList.remove(indice2);
                    jointPairList.remove(indice1);

                    tempSet1.addAll(tempSet2);
                    jointPairList.add(tempSet1);
                }
            }
        }
        return jointPairList;
    }



    /**
     *This method would take the larger sets of indices and find the correspondinc character from the string and then sort them
     *For example the following string abcdef with the following pairs of indices [1,2][2,3],[4,5]
     *the two first indices would overlap giving [1,2,3],[4,5] only character on indice 1,2,3 could be swapped together
     *and can be swapped with indice 4 or 5 for example. As such for each sets of indices
     *we want to get the character corresponding to that sets then sort them
     */
    public HashMap<TreeSet<Integer>,ArrayList<Character>> retrieveChar(String str,ArrayList<TreeSet<Integer>> jointpairList )
    {
        HashMap<TreeSet<Integer>,ArrayList<Character>> indiceCharMapping = new HashMap<>();

        int jointListSize = jointpairList.size();

        for(int i = 0; i < jointListSize;i++)
        {
            TreeSet<Integer> tempSet = jointpairList.get(i);
            ArrayList<Character> charList = new ArrayList<>();
            for(Integer value : tempSet)
            {
                char tempChar = str.charAt(value-1);
                charList.add(tempChar);
            }

            Collections.sort(charList,Collections.reverseOrder());
            indiceCharMapping.put(tempSet,charList);
        }
        return indiceCharMapping;
    }

    /**
     *This method is straightforward it just replace the character with the most optimum one obtain from the previous steps
     *Then return the proper result.
     */
    public String swapStringOrder(String str,HashMap<TreeSet<Integer>,ArrayList<Character>> indiceCharMapping)
    {
        Iterator it = indiceCharMapping.entrySet().iterator();
        char[] swappedCharString = str.toCharArray();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            TreeSet<Integer> tempSet = (TreeSet<Integer>)pair.getKey();
            ArrayList<Character> tempCharList = (ArrayList<Character>)pair.getValue();
            int charCounter =0;
            for(Integer value: tempSet)
            {
                char tempChar = tempCharList.get(charCounter);
                swappedCharString[value-1] = tempChar;
                charCounter++;
            }
        }
        return new String(swappedCharString);
    }


    /**
     *The following is the brute force method that would not work well with larger dataset.
     */
    String swapLexOrder(String str, int[][] pairs) {

        HashMap<String,Integer> lexOrder = new HashMap<String,Integer>();
        ArrayDeque<String> tempQueue = new ArrayDeque();

        lexOrder.put(str,-1);
        tempQueue.add(str);

        int pairsize = pairs.length;
        String highest = str;
        String tempSwap = tempQueue.poll();

        while(tempSwap != null)
        {
            for(int i = 0 ; i < pairsize;i++)
            {

                int[] pairIndices = pairs[i];
                int first = pairIndices[0]-1;
                int second = pairIndices[1]-1;
                tempSwap = swapChar(tempSwap,first,second);
                if(!lexOrder.containsKey(tempSwap))
                {
                    lexOrder.put(tempSwap,i);
                    tempQueue.add(tempSwap);
                    if(highest.compareTo(tempSwap) < 0)
                    {
                        highest = tempSwap;
                    }
                }
            }

            tempSwap = tempQueue.poll();
        }

        return highest;
    }

    public String swapChar(String string,int first,int second)
    {
        char[] cString = string.toCharArray();

        char temp1 = cString[first];
        cString[first] = cString[second];
        cString[second] = temp1;

        return new String(cString);
    }

