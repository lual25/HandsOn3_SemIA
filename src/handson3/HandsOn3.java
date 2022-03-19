/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handson3;
import jade.core.Agent;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author Luis
 */
public class HandsOn3 extends Agent{
    public void setup()
    {
        Population p = new Population(10);
        p.printPopulaton();
        for(int i=0; i<50; i++)
        {
            p.produceNewPopulation(50);
            p.printPopulaton();
        }
        doDelete();
    }
    public final class Population {
    Individual[] population = new Individual[10];
    int[] probabilities = new int[10];
    int populationSize;
    int totalFitness;
    int generation=0;
    Population(int populationSize)
    {
        this.populationSize = populationSize;
        for(int i=0; i<populationSize; i++)
        {
            population[i] = new Individual();
        }
        getProbabilities();
    }
    void printPopulaton()
    {
        System.out.println("Gen: "+generation+" Global Fitness: "+totalFitness);
        //int i=0;
        for (Individual population1 : population) {
            population1.printIndividual();
            //System.out.println(probabilities[i]);
            //i++;
        }
        selectParent().printIndividual();
    }
    int selectGlobalFitness()
    {
        totalFitness=0;
        for (Individual population1 : population) {
            totalFitness += population1.getFitness();
        }
        
        return totalFitness;
    }
    Individual selectParent()
    {
        Individual parent = new Individual();
            int random = ThreadLocalRandom.current().nextInt(0, probabilities[probabilities.length-1] + 1);
            for(int i=0; i<probabilities.length; i++)
            {
                if(random<=probabilities[i])
                {
                    parent=population[i];
                    break;
                }
            }
        return parent;
    }
    int[] getProbabilities()
    {
        int[] prob = new int[population.length];
        int q=0;
        int p;
        for(int i= 0; i<population.length; i++)
        {
            p = (population[i].getFitness()*100)/selectGlobalFitness();
            q+=p;
            prob[i]=q;
        }
        probabilities= prob;
        return prob;
    }
    Individual[] produceNewPopulation(int crossoverRate)
    {
        Individual[] newPopulation = population;
        //int[] p = getProbabilities(fitness(population));
        for(int i=0; i<population.length; i++)
        {
            if(crossoverRate>ThreadLocalRandom.current().nextInt(0, 100 + 1))
            {
                Individual secondParent = selectParent();
                newPopulation[i].crossOver(newPopulation[i], secondParent);
            }
        }
        population=newPopulation;
        getProbabilities();
        generation++;
        return newPopulation;
        
    }
}
    public final class Individual {
    String individual=randomGen();
    int fitness;
    Individual()
    {
        individual=randomGen();
        selectFitness();
    }
    String getIndividual()
    {
        return individual;
    }
    String randomGen()
    {
        String object = "";
        for(int i=0; i<10; i++)
        {
            object+= String.valueOf(ThreadLocalRandom.current().nextInt(0, 1 + 1)); 
        }
        return object;
    }
    void printIndividual()
    {
        System.out.println(individual+" Fitness: "+fitness);
    }
    int selectFitness()
    {
        fitness = 0;
            for(int j=0; j<individual.length(); j++)
                if(individual.charAt(j) == '1')
                {
                    fitness++;
                }
            return fitness;
    }
    int getFitness()
    {
        return fitness;
    }
    String crossOver(Individual parent1, Individual parent2)
    {
        String children;
        int crossoverpoint = ThreadLocalRandom.current().nextInt(0, parent1.getIndividual().length() + 1);
        children = parent1.getIndividual().substring(0, crossoverpoint) + parent2.getIndividual().substring(crossoverpoint);
        individual = children;
        selectFitness();
        return children;
    }
    String mutation(int mutationRate)
    {
        StringBuilder aux = new StringBuilder(individual);
        for(int i=0; i<aux.length(); i++)
        {
            if(mutationRate<=ThreadLocalRandom.current().nextInt(0, 100 + 1))
            {
                if(aux.charAt(i)=='0')
                {
                    aux.setCharAt(i, '1');
                }
                else
                    aux.setCharAt(i, '0');

            }
        }
        return aux.toString();
    }
}
    
    
}
